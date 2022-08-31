package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.PaymentModeTypeEnum;
import com.spms.mvc.Enumeration.SystemDataInt;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.AccSaleInvoiceGenerationDao;
import com.spms.mvc.dao.AssetOpeningDao;
import com.spms.mvc.dao.FixedAssetSaleDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.FaSaleRecord;
import com.spms.mvc.entity.FaSaleRecordDetail;
import com.spms.mvc.library.helper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jigme.dorji on 9/16/2021.
 */
@Service
public class FixedAssetSaleService {

    @Autowired
    FixedAssetSaleDao fixedAssetSaleDao;

    @Autowired
    AssetOpeningDao assetOpeningDao;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private AccSaleInvoiceGenerationDao accSaleInvoiceGenerationDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private VoucherGroupListService voucherGroupListService;

    public List<OpeningAndBuyingDTO> getFaItemDetails(BigInteger assetDetailId, CurrentUser currentUser) {
        return fixedAssetSaleDao.getFaItemDetails(assetDetailId, currentUser.getCompanyId());
    }

    public List<DropdownDTO> getItemList(CurrentUser currentUser) {
        return fixedAssetSaleDao.getItemList(currentUser.getCompanyId());
    }

    public List<DropdownDTO> getItemCodeList(BigInteger assetDetailId) {
        return fixedAssetSaleDao.getItemCodeList(assetDetailId);
    }

    public ResponseMessage saveSaleItemDetails(SaleItemDTO saleItemDTO, CurrentUser currentUser) {

        ResponseMessage responseMessage = new ResponseMessage();

        String receiptMemoNo = "";
        double assetSalesProfit = 0.0;
        double assetSalesLoss = 0.0;
        double assetSalesEqual = 0.0;

        try {

            Integer voucherNo;
            Integer currentReceiptSerialNo = 0;

            /*   Auto create Cash ledger*/
            ledgerService.getLedgerIdByLedgerName("Cash in Hand", currentUser,
                    AccountTypeEnum.CASH.getValue()); //create cash ledger if not exists

            //generate receipt memo No
            if (saleItemDTO.getVoucherNo() != null) {
                receiptMemoNo = saleItemDTO.getReceiptMemoNo();
                voucherNo = saleItemDTO.getVoucherNo();
                currentReceiptSerialNo = fixedAssetSaleDao.getReceiptSerial(currentUser.getCompanyId());
                voucherGroupListService.deleteLedgerVoucherDetails(getVoucherNoByReceiptMemoNo(receiptMemoNo,
                        currentUser), VoucherTypeEnum.SALES.getValue(), currentUser);
            } else {
                currentReceiptSerialNo = fixedAssetSaleDao.getReceiptSerial(currentUser.getCompanyId());
                if (currentReceiptSerialNo == null) {
                    fixedAssetSaleDao.insertToReceiptSerialCounter(currentUser.getCompanyId());
                }
                currentReceiptSerialNo = currentReceiptSerialNo == null ? 0 : currentReceiptSerialNo;
                currentReceiptSerialNo = currentReceiptSerialNo + 1;
                receiptMemoNo = String.format("%06d", currentReceiptSerialNo);

                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.SALES.getValue(),
                        currentUser.getCompanyId(), currentUser.getFinancialYearId());
            }

            //region save sale details
            if (saleItemDTO.getAmount() > 0) {
                //save sale record
                FaSaleRecord faSaleRecord = new FaSaleRecord();
                faSaleRecord.setDiscount(saleItemDTO.getDiscountRate());
                faSaleRecord.setSaleDate(saleItemDTO.getSaleDate());
                faSaleRecord.setSetDate(currentUser.getCreatedDate());
                faSaleRecord.setCreatedBy(currentUser.getLoginId());
                faSaleRecord.setCompanyId(currentUser.getCompanyId());
                faSaleRecord.setFinancialYearId(currentUser.getFinancialYearId());
                faSaleRecord.setReceiptMemoNo(receiptMemoNo);
                faSaleRecord.setVoucherNo(voucherNo);
                faSaleRecord.setPartyId(savePartyDetail(saleItemDTO, currentUser));
                faSaleRecord.setSaleInType(saleItemDTO.getIsCash());
                BigInteger saleRecordId = fixedAssetSaleDao.saveFaSaleRecord(faSaleRecord);

                for (SaleItemListDTO saleItemListDTO : saleItemDTO.getSaleItemListDTO()) {

                    //net amount is amount after depreciation
                    Double netAmount = calculateNetValueOfAsset(saleItemListDTO.getAssetCode(), currentUser, saleItemDTO.getSaleDate());

                    if (saleItemListDTO.getSellingPrice() > netAmount) {
                        assetSalesProfit = assetSalesProfit + (saleItemListDTO.getSellingPrice() - netAmount);
                    }

                    if (saleItemListDTO.getSellingPrice() < netAmount) {
                        assetSalesLoss = assetSalesLoss + (netAmount - saleItemListDTO.getSellingPrice());
                    }

                    if (saleItemListDTO.getSellingPrice().equals(netAmount)) {
                        assetSalesEqual = assetSalesEqual + saleItemListDTO.getSellingPrice();
                    }

                    saveToSaleRecordDetail(currentUser, saleRecordId,
                            saleItemListDTO.getAssetCode(), saleItemListDTO.getSellingPrice(),
                            netAmount);

                    //update the status is purchase
                    fixedAssetSaleDao.updateStatusAssetPurchase(saleItemListDTO.getAssetCode());

                }
                //endregion

                String ledgerId;
                VoucherDTO voucherDTO = new VoucherDTO();
                List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();

                //get detail of asset sale by receipt memo number.
                List<OpeningAndBuyingListDTO> openingAndBuyingListDTO = fixedAssetSaleDao.getAssetSaleRecordByReceiptMemoNo(receiptMemoNo,
                        currentUser.getCompanyId());

                for (OpeningAndBuyingListDTO openingAndBuyingList : openingAndBuyingListDTO) {

                    ledgerId = ledgerService.getLedgerIdByLedgerName(ledgerService.getAccountTypeNameByAccType(openingAndBuyingList.getAccTypeId()),
                            currentUser, openingAndBuyingList.getAccTypeId());

                    VoucherDetailDTO voucherDetailDTODTO = new VoucherDetailDTO();
                    voucherDetailDTODTO.setDrcrAmount(openingAndBuyingList.getTotalAmount());
                    voucherDetailDTODTO.setLedgerId(ledgerId);
                    voucherDetailDTOs.add(voucherDetailDTODTO);
                }

                //When particular asset is sold at its net value after depreciation.
                if (assetSalesEqual > 0.0) {

                    ledgerId = ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.SALES.getText(), currentUser,
                            AccountTypeEnum.SALES.getValue());

                    VoucherDetailDTO voucherSaleEqualDTO = new VoucherDetailDTO();
                    voucherSaleEqualDTO.setDrcrAmount(assetSalesEqual);
                    voucherSaleEqualDTO.setLedgerId(ledgerId);
                    voucherDetailDTOs.add(voucherSaleEqualDTO);
                }

                //When the asset is sold at a higher price than the net value after depreciation.
                if (assetSalesProfit > 0.0) {
                    ledgerId = ledgerService.getLedgerIdByLedgerName("Asset Sale Income", currentUser,
                            AccountTypeEnum.OTHER_INCOME.getValue());

                    VoucherDetailDTO voucherSaleHigherDTO = new VoucherDetailDTO();
                    voucherSaleHigherDTO.setDrcrAmount(assetSalesProfit);
                    voucherSaleHigherDTO.setLedgerId(ledgerId);
                    voucherDetailDTOs.add(voucherSaleHigherDTO);
                }

                //When asset is sold at price less than the net value of the particular asset as on date.
                if (assetSalesLoss > 0.0) {
                    ledgerId = ledgerService.getLedgerIdByLedgerName("Asset Sales Loss", currentUser,
                            AccountTypeEnum.INDIRECT_COST.getValue());

                    VoucherDetailDTO voucherDetailLesserDTO = new VoucherDetailDTO();
                    voucherDetailLesserDTO.setDrcrAmount(AccountingUtil.drAmount(assetSalesLoss));
                    voucherDetailLesserDTO.setLedgerId(ledgerId);
                    voucherDetailDTOs.add(voucherDetailLesserDTO);
                }

                voucherDTO.setVoucherNo(voucherNo);
                voucherDTO.setVoucherTypeId(VoucherTypeEnum.SALES.getValue());
                voucherDTO.setNarration("Fixed Asset Sales Entry");
                voucherDTO.setVoucherEntryDate(saleItemDTO.getSaleDate());

                //endregion

                double amount;
                //region Dr
                //Sale in Cash
                VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
                if (Objects.equals(saleItemDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {
                    amount = saleItemDTO.getAmount();
                    voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(amount));
                    voucherDetailDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
                    voucherDetailDTOs.add(voucherDetailDTO);
                }

                //Sale in Bank
                if (Objects.equals(saleItemDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
                    amount = saleItemDTO.getAmount();
                    voucherDetailDTO = new VoucherDetailDTO();
                    voucherDetailDTO.setBankLedgerId(saleItemDTO.getBankLedgerId());
                    voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(amount));
                    voucherDetailDTO.setIsCash(saleItemDTO.getIsCash());
                    voucherDetailDTOs.add(voucherDetailDTO);
                }

                //Sale in credit
            /* Whether the sale is made either from cash or bank, if there is remaining amount left,
             system will take remaining amount as credit for particular party*/
                if ((saleItemDTO.getPartyName() != null && !saleItemDTO.getPartyName().equals("")) || Objects.equals(saleItemDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())
                        || saleItemDTO.getAmtReturn() < 0) { // for credit party ledger
                    //create ledger for party
                    double creditAmount;
                    voucherDetailDTO = new VoucherDetailDTO();

                    voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                            saleItemDTO.getPartyName(), currentUser, AccountTypeEnum.RECEIVABLE.getValue()));

                    creditAmount = (saleItemDTO.getAmount());
                    voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(creditAmount));
                    voucherDetailDTO.setIsCash(saleItemDTO.getIsCash());
                    voucherDetailDTOs.add(voucherDetailDTO);
                }

                //voucher entry for both cash and bank
                if (Objects.equals(saleItemDTO.getIsCash(), PaymentModeTypeEnum.BANK_AND_CASH.getValue())) {
                    //Bank
                    VoucherDetailDTO cashBankDTO = new VoucherDetailDTO();
                    cashBankDTO.setBankLedgerId(saleItemDTO.getBankLedgerId());
                    cashBankDTO.setDrcrAmount(AccountingUtil.drAmount(Math.abs(saleItemDTO.getAmount() - (saleItemDTO.getDiscountRate() + saleItemDTO.getAmtReceived()))));
                    cashBankDTO.setIsCash(saleItemDTO.getIsCash());
                    voucherDetailDTOs.add(cashBankDTO);

                    //Compound voucher Cash
                    cashBankDTO = new VoucherDetailDTO();
                    cashBankDTO.setDrcrAmount(AccountingUtil.drAmount(saleItemDTO.getAmount() - (saleItemDTO.getDiscountRate() + saleItemDTO.getAmountReceivedInBank())));
                    cashBankDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
                    voucherDetailDTOs.add(cashBankDTO);
                }
                //endregion

                //region Dr for Discount Amount Voucher Entry
                if (saleItemDTO.getDiscountRate() > 0) {
                    VoucherDetailDTO voucherDiscountDetailDTO = new VoucherDetailDTO();
                    voucherDiscountDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                            "Discount", currentUser,
                            AccountTypeEnum.INDIRECT_COST.getValue()));
                    voucherDiscountDetailDTO.setDrcrAmount(AccountingUtil.drAmount(saleItemDTO.getDiscountRate()));
                    voucherDiscountDetailDTO.setIsCash(saleItemDTO.getIsCash());
                    voucherDetailDTOs.add(voucherDiscountDetailDTO);
                }

                voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
                voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

                //update receipt counter
                if (saleItemDTO.getVoucherNo() == null) {
                    fixedAssetSaleDao.updateReceiptSerial(currentReceiptSerialNo, currentUser.getCompanyId());
                }
            }
        } catch (Exception ex) {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        }
        responseMessage.setStatus(1);
        responseMessage.setText("Successful.");
        return responseMessage;
    }

    private Integer savePartyDetail(SaleItemDTO saleItemDTO, CurrentUser currentUser) {
        return AssetOpeningService.partyDetail(currentUser, saleItemDTO.getIsCash(), saleItemDTO.getPartyName(),
                accSaleInvoiceGenerationDao, saleItemDTO.getPartyAddress(),
                saleItemDTO.getPartyContactNo(), saleItemDTO.getPartyEmail());
    }

    private void saveToSaleRecordDetail(CurrentUser currentUser, BigInteger saleRecordId,
                                        String assetCode, double sellingPrice, double netSellingAmount) {
        FaSaleRecordDetail faSaleRecordDetail = new FaSaleRecordDetail();
        faSaleRecordDetail.setSaleRecordId(saleRecordId);
        faSaleRecordDetail.setSellingPrice(sellingPrice);
        faSaleRecordDetail.setNetAmount(netSellingAmount);
        faSaleRecordDetail.setAssetCode(assetCode);
        faSaleRecordDetail.setSetDate(currentUser.getCreatedDate());
        faSaleRecordDetail.setCreatedBy(currentUser.getLoginId());
        fixedAssetSaleDao.saveFaItemDetails(faSaleRecordDetail);
    }

    public Integer getVoucherNoByReceiptMemoNo(String receiptMemoNo, CurrentUser currentUser) {
        return fixedAssetSaleDao.getVoucherNoByReceiptMemoNo(receiptMemoNo, currentUser);
    }

    public Double calculateNetValueOfAsset(String assetCode, CurrentUser currentUser, Date asOnDate) throws ParseException {

        OpeningAndBuyingListDTO openingAndBuyingListDTO = fixedAssetSaleDao.getAssetDetailByAssetCode(assetCode, currentUser.getCompanyId());

        double netValue = 0.00;
        Double depreciationRate = openingAndBuyingListDTO.getAccTypeId().equals(AccountTypeEnum.BUILDING_AND_AMENITIES.getValue()) ? 0.03 : 0.15;

        Date financialYearStartDate = new SimpleDateFormat("dd-MMM-yyyy")
                .parse(currentUser.getFinancialYearFrom());

        Calendar cal = Calendar.getInstance();
        cal.setTime(financialYearStartDate);
        int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        long totalNoOfDaysDif = DateUtil.dayDifference(financialYearStartDate, asOnDate);

        Double purchaseValue = openingAndBuyingListDTO.getRate();

        if (openingAndBuyingListDTO.getOpeningBalance() != null) {

            Double depreciationValue = openingAndBuyingListDTO.getDepreciatedValue();

            double currentYearDepreciation = ((purchaseValue * depreciationRate) / numOfDays) * totalNoOfDaysDif;
            Double currentYearDepAsDate = currentYearDepreciation + (depreciationValue / openingAndBuyingListDTO.getQty()
                    .doubleValue());
            netValue = purchaseValue - currentYearDepAsDate;

        } else {

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(openingAndBuyingListDTO.getPurchaseDate().toString());

            totalNoOfDaysDif = DateUtil.dayDifference(date, asOnDate);
            Double currentYearDepreciation = ((purchaseValue * depreciationRate) / numOfDays) * totalNoOfDaysDif;
            netValue = purchaseValue - currentYearDepreciation;
        }

        return Math.round(netValue * 100.0) / 100.0;
    }

    public List<FixedAssetScheduleDTO> getFixedAssetSchedule(Date asOnDate, Integer companyId) {
        return fixedAssetSaleDao.getFixedAssetSchedule(asOnDate, companyId);
    }

    @Autowired
    AutoVoucherService autoVoucherService;

    public ResponseMessage deleteFixedAsset(CurrentUser currentUser, String particular, Double depCurrentYear, Double openingBalance, Date entryDate) throws ParseException {
        BigInteger assetDetailId = fixedAssetSaleDao.getAssetDetailId(particular, currentUser.getCompanyId());

        Integer accTypeId = assetOpeningDao.getAccTypeByAssetItem(assetDetailId);
        //Accounting Entry

        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();
        VoucherDTO voucherDTO = new VoucherDTO();

        Integer voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
        voucherDTO.setNarration("Asset Delete");
        voucherDTO.setVoucherEntryDate(entryDate);

        //Prepare voucher Entry
        VoucherDetailDTO voucherDrDTo = new VoucherDetailDTO();
        voucherDrDTo.setDrcrAmount(AccountingUtil.crAmount(depCurrentYear));
        voucherDrDTo.setLedgerId(autoVoucherService.getLedgerId("Depreciation", currentUser, AccountTypeEnum.INDIRECT_COST.getValue()));
        voucherDetailDTOList.add(voucherDrDTo);

        VoucherDetailDTO voucherCr = new VoucherDetailDTO();
        voucherCr.setDrcrAmount(AccountingUtil.drAmount(depCurrentYear));
        voucherCr.setLedgerId(autoVoucherService.getLedgerId(ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId));
        voucherDetailDTOList.add(voucherCr);
        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
        voucherDTO.setVoucherEntryDate(entryDate);

        voucherDTO.setVoucherNo(voucherNo);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        ledgerService.updateOpeningBalance(ledgerService.getLedgerIdByLedgerName(
                        ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId), currentUser.getCompanyId(),
                (-1 * openingBalance));

        assetOpeningDao.deleteItemFromDetailByAssetDetailId(assetDetailId);
        assetOpeningDao.deleteItemByAssetDetailId(assetDetailId);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setText("Deleted Successfully");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());


        return responseMessage;
    }
}
