package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.Enumeration.PaymentModeTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.AccSaleInvoiceGenerationDao;
import com.spms.mvc.dao.SaleItemDao;
import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.Discount;
import com.spms.mvc.entity.PartyDetail;
import com.spms.mvc.entity.SaleRecord;
import com.spms.mvc.entity.SaleRecordDetail;
import com.spms.mvc.global.service.BaseService;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by SonamPC on 16-Dec-16.
 */
@Service("saleItemService")
public class SaleItemService extends BaseService {

    @Autowired
    private SaleItemDao saleItemDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private SaleDetailService saleDetailService;

    @Autowired
    private AccSaleInvoiceGenerationDao accSaleInvoiceGenerationDao;


    public List<SaleItemDTO> getItemDetails(String itemCode, CurrentUser currentUser) {
        return saleItemDao.getItemDetails(itemCode, currentUser.getCompanyId());
    }


    //    @Transactional(rollbackOn = Exception.class)
    public String saveItemDetails(SaleItemDTO saleItemDTO, CurrentUser currentUser) throws ParseException {
        String receiptMemoNo = "";

        try {

            Integer voucherNo;
            Integer currentReceiptSerialNo;

            /*Auto calculation of total amount for manufacturing and construction*/
            if (BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) || BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())) {
                double totalAmount = 0.0;

                for (SaleItemListDTO saleItemListDTO : saleItemDTO.getSaleItemListDTO()) {
                    totalAmount = totalAmount + saleItemDao.getTotalAmountByItemCode(saleItemListDTO.getItemCode(),
                            currentUser.getCompanyId(), saleItemListDTO.getQty());
                }
                saleItemDTO.setAmount(totalAmount);
            }

            //generate receipt memo No
            if (saleItemDTO.getVoucherNo() != null) {
                receiptMemoNo = saleItemDTO.getReceiptMemoNo();
                voucherNo = saleItemDTO.getVoucherNo();
                currentReceiptSerialNo = saleItemDao.getReceiptSerial();
                saleDetailService.deleteSaleRelatedVoucher(receiptMemoNo, currentUser); //delete sale related voucher entry and sale records
            } else {
                currentReceiptSerialNo = saleItemDao.getReceiptSerial();
                if (currentReceiptSerialNo == 0) {
                    saleItemDao.insertToReceiptSerialCounter();
                }
                currentReceiptSerialNo = currentReceiptSerialNo + 1;
                receiptMemoNo = String.format("%06d", currentReceiptSerialNo);

                if (BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) || BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())) {
                    voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                            currentUser.getCompanyId(), currentUser.getFinancialYearId());
                } else {
                    /*   Auto create Cash ledger*/
                    ledgerService.getLedgerIdByLedgerName("Cash in Hand", currentUser,
                            AccountTypeEnum.CASH.getValue()); //create cash ledger if not exists

                    voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.SALES.getValue(),
                            currentUser.getCompanyId(), currentUser.getFinancialYearId());
                }
            }

            //region save sale details
            if (saleItemDTO.getAmount() > 0) {

                String ledgerId;
                VoucherDTO voucherDTO = new VoucherDTO();

                //validate the business type
                if (BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) || BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())) {

                    String ledgerName, narration;
                    Integer accountTypeId;

                    if (BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())) {
                        ledgerName = "Raw Material";
                        narration = "Raw Material Entry";
                        accountTypeId = AccountTypeEnum.RAW_MATERIAL.getValue();
                    } else {
                        ledgerName = "Material";
                        narration = "Material Entry";
                        accountTypeId = AccountTypeEnum.MATERIAL.getValue();
                    }
                    ledgerId = ledgerService.getLedgerIdByLedgerName(ledgerName, currentUser, accountTypeId);
                    voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
                    voucherDTO.setNarration(narration);
                } else {
                    ledgerId = ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.SALES.getText(), currentUser, AccountTypeEnum.SALES.getValue());
                    voucherDTO.setVoucherTypeId(VoucherTypeEnum.SALES.getValue());
                    voucherDTO.setNarration("Sales Entry");
                }

                voucherDTO.setVoucherEntryDate(saleItemDTO.getSaleDate());
                voucherDTO.setVoucherNo(voucherNo);

                //region cr voucher preparation
                List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
                VoucherDetailDTO voucherSaleDTO = new VoucherDetailDTO();
                voucherSaleDTO.setDrcrAmount(AccountingUtil.crAmount(Math.abs(saleItemDTO.getAmount())));
                voucherSaleDTO.setIsCash(saleItemDTO.getIsCash());
                voucherSaleDTO.setLedgerId(ledgerId);
                voucherDetailDTOs.add(voucherSaleDTO);
                //endregion

                //check party name already exists
                Integer partyId = AssetOpeningService.partyDetail(currentUser, saleItemDTO.getIsCash(), saleItemDTO.getPartyName(),
                        accSaleInvoiceGenerationDao, saleItemDTO.getPartyAddress(),
                        saleItemDTO.getPartyContactNo(), saleItemDTO.getPartyEmail());

                //save sale record
                SaleRecord saleRecord = new SaleRecord();
                saleRecord.setDiscount(saleItemDTO.getDiscountRate());
                saleRecord.setSaleDate(saleItemDTO.getSaleDate());
                saleRecord.setIssueTo(saleItemDTO.getIssueTo());
                saleRecord.setSetDate(currentUser.getCreatedDate());
                saleRecord.setCreatedBy(currentUser.getLoginId());
                saleRecord.setCompanyId(currentUser.getCompanyId());
                saleRecord.setFinancialYearId(currentUser.getFinancialYearId());
                saleRecord.setReceiptMemoNo(receiptMemoNo);
                saleRecord.setInvoiceNo(saleItemDTO.getInvoiceNo());
                saleRecord.setVoucherNo(voucherNo);
                saleRecord.setPartyId(partyId);
                saleRecord.setSaleInType(saleItemDTO.getIsCash());
                Integer saleRecordId = saleItemDao.saveSaleRecord(saleRecord);

                for (SaleItemListDTO saleItemListDTO : saleItemDTO.getSaleItemListDTO()) {
                    //region update the spill over qty
                    if (saleItemListDTO.getQty().compareTo(BigDecimal.ZERO) >= 1) {
                        saveToSaleRecordDetail(currentUser, saleRecordId, saleItemListDTO.getQty(),
                                saleItemListDTO);
                    }
                    //endregion
                }
                //endregion

                if (BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) || BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())) {
                    VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
                    voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(saleItemDTO.getAmount()));
                    voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                            BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType()) ? "Raw Material" : "Material Consumed",
                            currentUser, AccountTypeEnum.DIRECT_COST.getValue()));
                    voucherDetailDTOs.add(voucherDetailDTO);
                } else {
                    //region Dr
                    double amount;
                    //Sale in Cash
                    VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
                    if (Objects.equals(saleItemDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {

                        if (saleItemDTO.getAmtReturn() < 0) {
                            amount = saleItemDTO.getAmtReceived();
                        } else {
                            amount = saleItemDTO.getAmount() - saleItemDTO.getDiscountRate();
                        }
                        voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(amount));
                        voucherDetailDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
                        voucherDetailDTOs.add(voucherDetailDTO);
                    }

                    //Sale in Bank
                    if (Objects.equals(saleItemDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
                        if (saleItemDTO.getAmtReturn() < 0) {
                            amount = saleItemDTO.getAmtReceived();
                        } else {
                            amount = saleItemDTO.getAmount() - saleItemDTO.getDiscountRate();
                        }
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

                        if (saleItemDTO.getAmtReturn() < 0) {
                            creditAmount = Math.abs(saleItemDTO.getAmtReturn());
                        } else {
                            creditAmount = (saleItemDTO.getAmount() - saleItemDTO.getDiscountRate());
                        }
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

                    //for discount
                    if (saleItemDTO.getDiscountRate() != null && saleItemDTO.getDiscountRate() > 0) {
                        Discount discount = new Discount();
                        discount.setReceiptNo(receiptMemoNo);
                        discount.setDiscountAmount(saleItemDTO.getDiscountRate());
                        discount.setSaleDate(saleItemDTO.getSaleDate());
                        saleItemDao.saveDiscount(discount);
                    }
                }

                voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
                voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

                //update receipt counter
                if (saleItemDTO.getVoucherNo() == null) {
                    saleItemDao.updateReceiptSerial(currentReceiptSerialNo);
                }
            }
        } catch (Exception ex) {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        }
        return receiptMemoNo;
    }

    private void saveToSaleRecordDetail(CurrentUser currentUser, Integer saleRecordId, BigDecimal qty,
                                        SaleItemListDTO saleItemListDTO) {
        SaleRecordDetail saleRecordDetail = new SaleRecordDetail();
        saleRecordDetail.setSaleRecordId(saleRecordId);
        saleRecordDetail.setQty(qty);
        if (BusinessType.Construction.getValue().equals(currentUser.getBusinessType()) || BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())) {
            saleRecordDetail.setSellingPrice(saleItemListDTO.getCostPrice());
        } else {
            saleRecordDetail.setSellingPrice(saleItemListDTO.getSellingPrice());
        }
        saleRecordDetail.setItemCode(saleItemListDTO.getItemCode());
        saleRecordDetail.setSetDate(currentUser.getCreatedDate());
        saleRecordDetail.setCreatedBy(currentUser.getLoginId());
        saleItemDao.saveItemDetails(saleRecordDetail);
    }

    public List<SaleItemListDTO> generateReceipt(String receiptMemoNo) {
        return saleItemDao.getSaleItemDetailByReceiptNo(receiptMemoNo);
    }

    public SaleItemDTO getSaleDetail(CurrentUser currentUser, Integer voucherNo) {

        SaleItemDTO saleItemDTO = saleItemDao.getMasterSaleDetail(voucherNo, currentUser.getCompanyId(),
                currentUser.getFinancialYearId());
        if (saleItemDTO != null) {
            saleItemDTO.setSaleItemListDTO(saleItemDao.getSaleDetail(
                    voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId()));
        }

        if (BusinessType.Manufacturing.getValue().equals(currentUser.getBusinessType())
                || BusinessType.Construction.getValue().equals(currentUser.getBusinessType())) {
            return saleItemDTO;
        }

        return getSaleInTypePaymentDetail(currentUser, voucherNo, saleItemDTO);
    }

    private SaleItemDTO getSaleInTypePaymentDetail(CurrentUser currentUser, Integer voucherNo, SaleItemDTO saleItemDTO) {

        SaleItemDTO saleItemPaymentModeDetail = saleItemDao.getSaleItemPaymentModeDetail(voucherNo, currentUser.getCompanyId(),
                currentUser.getFinancialYearId());

        if (isSaleCompoundVoucher(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId())) {

            List<SaleItemDTO> saleItemDTOList = saleItemDao.getCompoundVoucherList(voucherNo, currentUser.getCompanyId(),
                    currentUser.getFinancialYearId());

            return formatData(saleItemDTOList, saleItemDTO, Boolean.FALSE,
                    saleItemPaymentModeDetail.getSaleInType(), currentUser, voucherNo);
        } else {

            if (saleItemPaymentModeDetail.getSaleInType().equals(PaymentModeTypeEnum.CASH.getValue())) {
                List<SaleItemDTO> saleItemCashDTOList = saleItemDao.isSaleInCash(voucherNo, currentUser.getCompanyId(),
                        currentUser.getFinancialYearId());

                if (saleItemCashDTOList != null) {
                    return formatData(saleItemCashDTOList, saleItemDTO, Boolean.TRUE, saleItemPaymentModeDetail.getSaleInType(), currentUser, voucherNo);
                }
            }

            if (saleItemPaymentModeDetail.getSaleInType().equals(PaymentModeTypeEnum.BANK.getValue())) {
                List<SaleItemDTO> saleBankDTOList = saleItemDao.isSaleInBank(voucherNo,
                        currentUser.getCompanyId(),
                        currentUser.getFinancialYearId());

                if (saleBankDTOList != null) {
                    return formatData(saleBankDTOList, saleItemDTO, Boolean.TRUE, saleItemPaymentModeDetail.getSaleInType(), currentUser, voucherNo);
                }
            }

            if (saleItemPaymentModeDetail.getSaleInType().equals(PaymentModeTypeEnum.CREDIT.getValue())) {
                return getCreditDetail(voucherNo, currentUser, saleItemDTO);
            }
        }
        return saleItemDTO;
    }

    private SaleItemDTO getCreditDetail(Integer voucherNo, CurrentUser currentUser, SaleItemDTO saleItemDTO) {

        SaleItemDTO creditSaleDetailDTO = saleItemDao.isSaleInCredit(voucherNo,
                currentUser.getCompanyId(), currentUser.getFinancialYearId());

        if (creditSaleDetailDTO != null) {
            saleItemDTO.setIsCash(PaymentModeTypeEnum.CREDIT.getValue());
            saleItemDTO.setPartyName(ledgerService.getNameByLedgerId(creditSaleDetailDTO.getLedgerId(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId()));
        }
        return saleItemDTO;
    }


    private SaleItemDTO formatData(List<SaleItemDTO> saleItemDTOList, SaleItemDTO saleItemDTO, Boolean aTrue, Integer saleInType, CurrentUser currentUser, Integer voucherNo) {
        for (SaleItemDTO saleItemList : saleItemDTOList) {
            //Transaction In Bank
            if (saleItemList.getAccTypeId().equals(AccountTypeEnum.BANK.getValue())) {
                saleItemDTO.setBankLedgerId(saleItemList.getLedgerId());
                if (aTrue) {
                    saleItemDTO.setAmtReceived(Math.abs(saleItemList.getAmount()));
                } else {
                    saleItemDTO.setAmountReceivedInBank(Math.abs(saleItemList.getAmount()));
                }
            }
            //Transaction In Cash
            if (saleItemList.getAccTypeId().equals(AccountTypeEnum.CASH.getValue())) {
                saleItemDTO.setAmtReceived(Math.abs(saleItemList.getAmount()));
            }
            //Transaction for Discount
            if (saleItemList.getAccTypeId().equals(AccountTypeEnum.INDIRECT_COST.getValue())) {
                saleItemDTO.setDiscountRate(Math.abs(saleItemList.getAmount()));
            }
            //credit sale
            if (saleItemList.getAccTypeId().equals(AccountTypeEnum.RECEIVABLE.getValue())) {
                getCreditDetail(voucherNo, currentUser, saleItemDTO);
            }
            saleItemDTO.setIsCash(saleInType);
        }
        return saleItemDTO;
    }

    private Boolean isSaleCompoundVoucher(Integer voucherNo, Integer companyId, Integer financialYearId) {
        List<Integer> accountTypeList = saleItemDao.isSaleCompoundVoucher(voucherNo, companyId, financialYearId);
        if (accountTypeList.contains(AccountTypeEnum.CASH.getValue()) && accountTypeList.contains(AccountTypeEnum.BANK.getValue())) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public BigDecimal getAvailableQty(String itemCode, Integer companyId) {
        return saleItemDao.getAvailableQty(itemCode, companyId);
    }

    public List<DropdownDTO> getItemCodeList(CurrentUser currentUser) {
        return saleItemDao.getItemCodeList(currentUser.getCompanyId());
    }

    public List<SaleItemDTO> getIssueItemDetails(String itemCode, CurrentUser currentUser) {
        return saleItemDao.getIssueItemDetails(itemCode, currentUser.getCompanyId());
    }
}
