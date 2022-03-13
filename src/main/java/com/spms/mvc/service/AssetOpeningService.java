package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.PaymentModeTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.AccSaleInvoiceGenerationDao;
import com.spms.mvc.dao.AssetOpeningDao;
import com.spms.mvc.dto.OpeningAndBuyingDTO;
import com.spms.mvc.dto.OpeningAndBuyingListDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.FaPurchase;
import com.spms.mvc.entity.FaPurchaseDetail;
import com.spms.mvc.entity.FaPurchaseMaster;
import com.spms.mvc.entity.PartyDetail;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description: OpeningAndBuyingService
 * Date:  2021-Sep-16
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Sep-16
 * Change Description:
 * Search Tag:
 */

@Service("assetOpeningService")
public class AssetOpeningService {

    @Autowired
    private AssetOpeningDao assetOpeningDao;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private AccSaleInvoiceGenerationDao accSaleInvoiceGenerationDao;


    public List<DropdownDTO> getFixedAssetGroupList() {
        return assetOpeningDao.getFixedAssetGroupList();
    }

    //    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(CurrentUser currentUser, OpeningAndBuyingDTO openingAndBuyingDTO) throws ParseException {

        String ledgerName = null;
        BigInteger purchaseMasterId;
        BigInteger purchaseId;
        BigInteger faPurchaseDetailId;

        ResponseMessage responseMessage = new ResponseMessage();

        //check party name already exists
        Integer partyId = null;

        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue()) || (openingAndBuyingDTO.getPartyName() != null && !openingAndBuyingDTO.getPartyName().equals(""))) { //applies to only party

            partyId = accSaleInvoiceGenerationDao.getPartyIdIFExists(currentUser.getCompanyId(),
                    openingAndBuyingDTO.getPartyName());

            if (partyId == null) {
                PartyDetail partyDetail = new PartyDetail();
                partyId = accSaleInvoiceGenerationDao.getMaxPartyId() + 1;
                partyDetail.setPartyId(partyId);
                partyDetail.setPartyName(openingAndBuyingDTO.getPartyName());
                partyDetail.setPartyAddress(openingAndBuyingDTO.getPartyAddress());
                partyDetail.setPartyContactNo(openingAndBuyingDTO.getPartyContactNo());
                partyDetail.setPartyEmail(openingAndBuyingDTO.getPartyEmail());
                partyDetail.setCompanyId(currentUser.getCompanyId());
                partyDetail.setSetDate(currentUser.getCreatedDate());
                partyDetail.setCreatedBy(currentUser.getLoginId());
                accSaleInvoiceGenerationDao.savePartyDetail(partyDetail);
            }
        }

        //insert into master
        FaPurchaseMaster faPurchaseMaster = new FaPurchaseMaster();

        if (openingAndBuyingDTO.getPurchaseMasterId() == null) {
            purchaseMasterId = assetOpeningDao.getMaxMasterFaPurchaseId(currentUser.getCompanyId());
            purchaseMasterId = purchaseMasterId == null ? BigInteger.ONE : purchaseMasterId.add(BigInteger.ONE);
        } else {
            purchaseMasterId = openingAndBuyingDTO.getPurchaseMasterId();
        }

        faPurchaseMaster.setPartyId(partyId);
        faPurchaseMaster.setPurchaseMasterId(purchaseMasterId);
        faPurchaseMaster.setAsOnDate(openingAndBuyingDTO.getPurchaseDate());
        faPurchaseMaster.setPurchaseInvoiceNo(openingAndBuyingDTO.getPurchaseInvoiceNo());
        faPurchaseMaster.setPaidInType(openingAndBuyingDTO.getPaidInType());
        faPurchaseMaster.setVoucherNo(openingAndBuyingDTO.getVoucherNo());
        faPurchaseMaster.setCompanyId(currentUser.getCompanyId());
        faPurchaseMaster.setCreatedBy(currentUser.getLoginId());
        faPurchaseMaster.setCreatedDate(currentUser.getCreatedDate());
        assetOpeningDao.saveToMasterTable(faPurchaseMaster);

        for (OpeningAndBuyingListDTO openingAndBuyingListDTO : openingAndBuyingDTO.getOpeningAndBuyingListDTO()) {

            //save to main table
            FaPurchase fa_purchase = new FaPurchase();
            if (openingAndBuyingListDTO.getFaPurchaseId() == null) {
                purchaseId = assetOpeningDao.getMaxFaPurchaseId(currentUser.getCompanyId());
                purchaseId = purchaseId == null ? BigInteger.ONE : purchaseId.add(BigInteger.ONE);
            } else {
                purchaseId = openingAndBuyingListDTO.getFaPurchaseId();
            }
            fa_purchase.setFaPurchaseId(purchaseId);
            fa_purchase.setPurchaseMasterId(purchaseMasterId);
            fa_purchase.setDepreciatedValue(openingAndBuyingListDTO.getDepreciatedValue());
            fa_purchase.setOpeningBalance(openingAndBuyingListDTO.getOpeningBalance());
            fa_purchase.setPurchaseDate(openingAndBuyingListDTO.getPurchaseDate() == null ?
                    openingAndBuyingDTO.getPurchaseDate() : openingAndBuyingListDTO.getPurchaseDate());
            fa_purchase.setAssetDetailId(openingAndBuyingListDTO.getAssetDetailId());
            fa_purchase.setRate(openingAndBuyingListDTO.getRate());
            fa_purchase.setQty(openingAndBuyingListDTO.getQty());
            fa_purchase.setCreatedBy(currentUser.getLoginId());
            fa_purchase.setCreatedDate(currentUser.getCreatedDate());
            assetOpeningDao.save(fa_purchase);

            //save to detail table
            BigDecimal initialQty = openingAndBuyingListDTO.getQty();
            while (initialQty.compareTo(BigDecimal.ZERO) > 0) {

                FaPurchaseDetail faPurchaseDetail = new FaPurchaseDetail();

                if (openingAndBuyingListDTO.getFaPurchaseDetailId() == null) {
                    faPurchaseDetailId = assetOpeningDao.getMaxFaPurchaseDetailId(currentUser.getCompanyId());
                    faPurchaseDetailId = faPurchaseDetailId == null ? BigInteger.ONE : faPurchaseDetailId.add(BigInteger.ONE);
                } else {
                    faPurchaseDetailId = openingAndBuyingListDTO.getFaPurchaseDetailId();
                }

                faPurchaseDetail.setFaPurchaseDetailId(faPurchaseDetailId);
                faPurchaseDetail.setFaPurchaseId(purchaseId);
                faPurchaseDetail.setStatus('N');
                faPurchaseDetail.setAssetCode(generateAssetCode(currentUser.getCompanyId()));
                faPurchaseDetail.setCreatedBy(currentUser.getLoginId());
                faPurchaseDetail.setCreatedDate(currentUser.getCreatedDate());
                assetOpeningDao.savePurchaseDetail(faPurchaseDetail);

                initialQty = initialQty.subtract(BigDecimal.ONE);
            }
        }

        //perform account
        if (openingAndBuyingDTO.getPurchaseInvoiceNo() != null) {
            performAccounting(openingAndBuyingDTO, currentUser);
        } else {

            List<OpeningAndBuyingDTO> openingAndBuyingDTOList = assetOpeningDao.getOpeningInvoiceDetailList(
                    currentUser.getCompanyId());

            for (OpeningAndBuyingDTO openingAndBuyingDTOI : openingAndBuyingDTOList) {
                if (openingAndBuyingDTOI.getAccTypeId().equals(AccountTypeEnum.FURNITURE_FIXTURE.getValue())) {
                    ledgerName = AccountTypeEnum.FURNITURE_FIXTURE.getText();
                } else {
                    ledgerName = AccountTypeEnum.find(openingAndBuyingDTOI.getAccTypeId());
                }

                ledgerService.updateOpeningBalance(ledgerService.getLedgerIdByLedgerName(ledgerName,
                        currentUser, openingAndBuyingDTOI.getAccTypeId()), currentUser.getCompanyId(),
                        openingAndBuyingDTOI.getAmount());
            }
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Successfully saved.");
        return responseMessage;

    }

    private String generateAssetCode(Integer companyId) {

        String maxAssetCodeSerialNo = assetOpeningDao.getMaxAssetCodeSerialNo(companyId);
        maxAssetCodeSerialNo = maxAssetCodeSerialNo == null ? "0" : maxAssetCodeSerialNo;

        if (!maxAssetCodeSerialNo.equals("0")) {
            maxAssetCodeSerialNo = maxAssetCodeSerialNo.substring(2, 8);
        }
        return "AC".concat(String.format("%06d", Integer.parseInt(maxAssetCodeSerialNo) + 1));
    }

    public List<OpeningAndBuyingDTO> getItemSuggestionList(CurrentUser currentUser) {
        return assetOpeningDao.getItemSuggestionList(currentUser.getCompanyId());
    }

    public void performAccounting(OpeningAndBuyingDTO openingAndBuyingDTO, CurrentUser currentUser) throws ParseException {

        //region cr voucher preparation
        Integer voucherNo;
        String ledgerName;
        VoucherDTO voucherDTO = new VoucherDTO();
        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();

        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue()) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
        } else {
            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
        }
        voucherDTO.setNarration("Buying fix asset");
        voucherDTO.setVoucherEntryDate(openingAndBuyingDTO.getPurchaseDate());
        voucherDTO.setVoucherNo(voucherNo);

        List<OpeningAndBuyingDTO> openingAndBuyingDTOList = assetOpeningDao.getInvoiceDetailList(openingAndBuyingDTO.getPurchaseInvoiceNo(),
                currentUser.getCompanyId());

        for (OpeningAndBuyingDTO openingAndBuyingDTOI : openingAndBuyingDTOList) {

            if (openingAndBuyingDTOI.getAccTypeId().equals(AccountTypeEnum.FURNITURE_FIXTURE.getValue())) {
                ledgerName = AccountTypeEnum.FURNITURE_FIXTURE.getText();
            } else {
                ledgerName = AccountTypeEnum.find(openingAndBuyingDTOI.getAccTypeId());
            }

            String ledgerId = ledgerService.getLedgerIdByLedgerName(ledgerName,
                    currentUser, openingAndBuyingDTOI.getAccTypeId());

            VoucherDetailDTO voucherDetailDTODTO = new VoucherDetailDTO();
            voucherDetailDTODTO.setDrcrAmount(AccountingUtil.drAmount(openingAndBuyingDTOI.getAmount()));
            voucherDetailDTODTO.setIsCash(openingAndBuyingDTO.getIsCash());
            voucherDetailDTODTO.setLedgerId(ledgerId);
            voucherDetailDTOs.add(voucherDetailDTODTO);
        }

        //region Dr
        double amount;
        //Sale in Cash
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {

            if (openingAndBuyingDTO.getAmtReturn() < 0) {
                amount = openingAndBuyingDTO.getAmtReceived();
            } else {
                amount = openingAndBuyingDTO.getAmount() - openingAndBuyingDTO.getDiscountRate();
            }
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(amount));
            voucherDetailDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //Sale in Bank
        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
            if (openingAndBuyingDTO.getAmtReturn() < 0) {
                amount = openingAndBuyingDTO.getAmtReceived();
            } else {
                amount = openingAndBuyingDTO.getAmount() - openingAndBuyingDTO.getDiscountRate();
            }
            voucherDetailDTO = new VoucherDetailDTO();
            voucherDetailDTO.setBankLedgerId(openingAndBuyingDTO.getBankLedgerId());
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(amount));
            voucherDetailDTO.setIsCash(openingAndBuyingDTO.getIsCash());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //Sale in credit
            /* Whether the sale is made either from cash or bank, if there is remaining amount left,
             system will take remaining amount as credit for particular party*/

        if ((openingAndBuyingDTO.getPartyName() != null && !openingAndBuyingDTO.getPartyName().equals("")) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())
                || openingAndBuyingDTO.getAmtReturn() < 0) { // for credit party ledger
            //create ledger for party
            double creditAmount;
            voucherDetailDTO = new VoucherDetailDTO();

            voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(
                    openingAndBuyingDTO.getPartyName(), currentUser, AccountTypeEnum.PAYABLE.getValue()));

            if (openingAndBuyingDTO.getAmtReturn() < 0) {
                creditAmount = Math.abs(openingAndBuyingDTO.getAmtReturn());
            } else {
                creditAmount = (openingAndBuyingDTO.getAmount() - openingAndBuyingDTO.getDiscountRate());
            }
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(creditAmount));
            voucherDetailDTO.setIsCash(openingAndBuyingDTO.getIsCash());
            voucherDetailDTOs.add(voucherDetailDTO);
        }
        //endregion

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
    }

    public List<OpeningAndBuyingDTO> loadAssetOpeningList(CurrentUser currentUser, BigInteger faPurchaseId) {
        return assetOpeningDao.loadAssetOpeningList(faPurchaseId);
    }
}