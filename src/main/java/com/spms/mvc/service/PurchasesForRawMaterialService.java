package com.spms.mvc.service;

import com.spms.mvc.Enumeration.*;
import com.spms.mvc.dao.PurchasesForRawMaterialDao;
import com.spms.mvc.dao.RawMaterialStorageDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.dto.PurchasesForRawMaterialSaveDTO;
import com.spms.mvc.entity.PurchaseCreditSupplierDetail;
import com.spms.mvc.entity.RawMaterialPurchasesStorage;
import com.spms.mvc.entity.RawMaterialStorage;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service(value = "purchasesForRawMaterialService")
public class PurchasesForRawMaterialService {

    @Autowired
    PurchasesForRawMaterialDao purchasesForRawMaterialDao;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private VoucherGroupListService voucherGroupListService;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private SupplierSetupService supplierSetupService;

    @Autowired
    RawMaterialStorageService rawMaterialStorageService;


    public ResponseMessage save(HttpServletRequest request, CurrentUser currentUser, PurchasesForRawMaterialSaveDTO purchasesForRawMaterialSaveDTO, RawMaterialStorageDTO rawMaterialStorageDTO) throws ParseException {

        ResponseMessage responseMessage = new ResponseMessage();

        //Only For Cash Ledger (IsCash==1)

        if (purchasesForRawMaterialSaveDTO.getIsCash() == 1) {
            if (isCashLedgerExist(currentUser, 4) == -1) {
                responseMessage.setStatus(0);
                responseMessage.setText("No cash ledger.");
                return responseMessage;
            }

            if (isCashLedgerExist(currentUser, 4) == 1) {
//                responseMessage.setStatus(1);
//                responseMessage.setText("Checking the Ledger Balance");
                try {

                    if (checkCashBalance(1, purchasesForRawMaterialSaveDTO.getTotalTranAmount(), currentUser).getStatus() == 0) {
                        responseMessage.setStatus(0);
                        responseMessage.setText("Insufficient cash balance.");
                        return responseMessage;

                    }
                    if (checkCashBalance(1, purchasesForRawMaterialSaveDTO.getTotalTranAmount(), currentUser).getStatus() == 1) {
                        if (voucherEntryByRawMaterialOpeningBalanceOrPurchases(currentUser, purchasesForRawMaterialSaveDTO).getStatus() == 1) {
                            purchasesForRawMaterialSave(currentUser, purchasesForRawMaterialSaveDTO);
                            responseMessage.setStatus(1);
                            responseMessage.setText("Successfully Saved !");
                            return responseMessage;
                        }

                    }


                } catch (ParseException parseException) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Error while Saving!");
                    return responseMessage;

                }
            }

        }

        if (purchasesForRawMaterialSaveDTO.getIsCash() == 2) {

            if (voucherEntryByRawMaterialOpeningBalanceOrPurchases(currentUser, purchasesForRawMaterialSaveDTO).getStatus() == 1) {
                purchasesForRawMaterialSave(currentUser, purchasesForRawMaterialSaveDTO);
                responseMessage.setStatus(1);
                responseMessage.setText("Successfully Purchased From Your Bank!");
                return responseMessage;
            }

        }
        if (purchasesForRawMaterialSaveDTO.getIsCash() == 3) {

//            if (Objects.equals(purchasesForRawMaterialSaveDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())) {
//                PurchaseCreditSupplierDetail purchaseCreditSupplierDetail = new PurchaseCreditSupplierDetail();
////            purchaseCreditSupplierDetail.setPurchaseId(purchaseId);
//                purchaseCreditSupplierDetail.setPurchaseInvoiceNo(purchasesForRawMaterialSaveDTO.getPurchaseInvoiceNo());
//                purchaseCreditSupplierDetail.setSupplierId(purchasesForRawMaterialSaveDTO.getSupplierId());
//                purchasesForRawMaterialDao.savePurchaseCreditSupplierDetail(purchaseCreditSupplierDetail);
//            }
//            purchasesForRawMaterialSave(currentUser,purchasesForRawMaterialSaveDTO);
//
//            responseMessage.setStatus(1);
//            responseMessage.setText("Successfully Purchased ! ");
//            return responseMessage;


            if (voucherEntryByRawMaterialOpeningBalanceOrPurchases(currentUser, purchasesForRawMaterialSaveDTO).getStatus() == 1) {
                if (Objects.equals(purchasesForRawMaterialSaveDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())) {
                    PurchaseCreditSupplierDetail purchaseCreditSupplierDetail = new PurchaseCreditSupplierDetail();
                    purchaseCreditSupplierDetail.setPurchaseInvoiceNo(purchasesForRawMaterialSaveDTO.getPurchaseInvoiceNo());
                    purchaseCreditSupplierDetail.setSupplierId(purchasesForRawMaterialSaveDTO.getSupplierId());
                    purchasesForRawMaterialDao.savePurchaseCreditSupplierDetail(purchaseCreditSupplierDetail);
                    purchasesForRawMaterialSave(currentUser, purchasesForRawMaterialSaveDTO);
                }
                responseMessage.setStatus(1);
                responseMessage.setText("Successfully Purchased ! ");
                return responseMessage;
            }

            if (purchasesForRawMaterialSaveDTO.getPurchaseInvoiceNo() == null && purchasesForRawMaterialSaveDTO.getIsCash() == null) {
                purchasesForRawMaterialSaveDTO.setPurchaseInvoiceNo("0");
            }

            purchasesForRawMaterialSaveDTO.getPurchaseInvoiceNo();


        }
        return responseMessage;
    }


    @Autowired
    RawMaterialStorageDao rawMaterialStorageDao;


    //----------------------------------------------Helpers Functions For Cash Ledger------------------------------------------------------

    // Validating the cash (Existing or not For Particular Company)
    public int isCashLedgerExist(CurrentUser currentUser, int acc_type) {
        if (purchasesForRawMaterialDao.isCashLedgerExist(currentUser, acc_type)) {
//            return "No cash ledger.";
            return -1;
        } else {
            return 1;
        }
    }

    public ResponseMessage checkCashBalance(Integer cash, double txnAmount, CurrentUser currentUser) throws ParseException {

        ResponseMessage responseMessage = new ResponseMessage();

        Double openingBalance = 0.0;
        Double totalCashAmount = 0.0;
        Double totalCashOutFlow = 0.0;

        Date currentPeriodFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearFrom());
        Date currentPeriodTo = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearTo());

        if (cash.equals(PaymentModeTypeEnum.CASH.getValue())) {

            String ledgerId = ledgerService.getLedgerIdByAccountTypeId(AccountTypeEnum.CASH.getValue(),
                    currentUser.getCompanyId());

            if (ledgerId != null) {
                openingBalance = voucherGroupListService.getOpeningBalance(ledgerId,
                        currentPeriodFrom, currentPeriodTo, currentUser).getOpeningBal();
            }
            totalCashAmount = purchasesForRawMaterialDao.getTotalCash(AccountTypeEnum.CASH.getValue(),
                    currentUser.getCompanyId(),
                    currentUser.getFinancialYearId());

            totalCashOutFlow = purchasesForRawMaterialDao.getTotalCashOutFlow(ledgerId,
                    currentUser.getCompanyId(),
                    currentPeriodFrom, currentPeriodTo);

            totalCashAmount = totalCashAmount == null ? 0.0 : totalCashAmount;
            totalCashOutFlow = totalCashOutFlow == null ? 0.0 : totalCashOutFlow;

            totalCashAmount = (Math.abs(totalCashAmount) + Math.abs(openingBalance)) - totalCashOutFlow;

            if (totalCashAmount < txnAmount) {
                responseMessage.setStatus(0);
                return responseMessage;
            }
        }
        responseMessage.setStatus(1);
        return responseMessage;
    }


//    public ResponseMessage checkCashBalance(CurrentUser currentUser,Double txnAmount) throws ParseException {
//
//        Date currentPeriodFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearFrom());
//        Date currentPeriodTo = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearTo());
//
//        ResponseMessage responseMessage=new ResponseMessage();
//
//        Double openingBalance = 0.0;
//
//        Double totalCashAmount = 0.0;
//        Double totalCashOutFlow = 0.0;
//
//        String ledgerIdPointer = ledgerService.getLedgerIdByAccountTypeId(4,
//                    currentUser.getCompanyId());
//
//            if (ledgerIdPointer!=null) {
//                openingBalance = voucherGroupListService.getOpeningBalance(ledgerIdPointer,
//                        currentPeriodFrom, currentPeriodTo, currentUser).getOpeningBal();
//            }
//                 totalCashAmount = purchasesForRawMaterialDao.getTotalCash(4,
//                 currentUser.getCompanyId(),
//                 currentUser.getFinancialYearId());
//
//            totalCashOutFlow =purchasesForRawMaterialDao.getTotalCashOutFlow(ledgerIdPointer,
//                 currentUser.getCompanyId(),
//                    currentPeriodFrom,currentPeriodTo);
//
//            totalCashAmount = totalCashAmount == null ? 0.0 : totalCashAmount;
//            totalCashOutFlow = totalCashOutFlow == null ? 0.0 : totalCashOutFlow;
//
//            totalCashAmount = (Math.abs(totalCashAmount) + Math.abs(openingBalance)) - totalCashOutFlow;
//
//            if (totalCashAmount < txnAmount) {
//                responseMessage.setStatus(0);
//                return responseMessage;
//            }
//            responseMessage.setStatus(1);
//            return responseMessage;
//
//
//}
    //---------------------------------------------Helper For Cash Ledger--------------------------------------------------------

    //---------------------------------------------------------------------------------------------------------------------


    public ResponseMessage voucherEntryByRawMaterialOpeningBalanceOrPurchases(CurrentUser currentUser, PurchasesForRawMaterialSaveDTO purchasesForRawMaterialSaveDTO) throws ParseException {
        VoucherDTO voucherDTO = new VoucherDTO();
        ResponseMessage responseMessage = new ResponseMessage();
        voucherDTO.setVoucherTypeId(AccountTypeEnum.EQUITY_OR_CAPITAL.getValue());
        voucherDTO.setVoucherEntryDate(purchasesForRawMaterialSaveDTO.getPurchaseDate());
        if (purchasesForRawMaterialSaveDTO.getVoucherNo() != null) {
            voucherDTO.setVoucherNo(purchasesForRawMaterialSaveDTO.getVoucherNo());
        } else {
            voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(
                    AccountTypeEnum.EQUITY_OR_CAPITAL.getValue(),
                    currentUser.getCompanyId(), currentUser.getFinancialYearId()));
        }

//        voucherDTO.setNarration("Purchase Entry");
        voucherDTO.setNarration("Raw Material");

        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
        //Dr Purchase
        VoucherDetailDTO purchaseDto = new VoucherDetailDTO();
        purchaseDto.setDrcrAmount(AccountingUtil.drAmount(purchasesForRawMaterialSaveDTO.getTotalTranAmount()));
        if (currentUser.getBusinessType().equals(BusinessType.Construction.getValue())) {
            purchaseDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.MATERIAL.getText(),
                    currentUser, AccountTypeEnum.MATERIAL.getValue()));
        } else {
//            purchaseDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.PURCHASE.getText(),
//                    currentUser, AccountTypeEnum.PURCHASE.getValue()));
            purchaseDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.RAW_MATERIAL.getText(),
                    currentUser, AccountTypeEnum.RAW_MATERIAL.getValue()));
        }

        purchaseDto.setIsCash(purchasesForRawMaterialSaveDTO.getIsCash());
        voucherDetailDTOs.add(purchaseDto);


        //cr bank/cash/Party
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        if (purchasesForRawMaterialSaveDTO.getSupplierId() != null) {

            String supplierName = supplierSetupService.getSupplierDetails(purchasesForRawMaterialSaveDTO.getSupplierId()).getSupplierName();


            voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(supplierName,
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));

        }
        voucherDetailDTO.setIsCash(purchasesForRawMaterialSaveDTO.getIsCash());
        voucherDetailDTO.setBankLedgerId(purchasesForRawMaterialSaveDTO.getBankLedgerId());
        voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(purchasesForRawMaterialSaveDTO.getTotalTranAmount()));
        voucherDetailDTO.setBankLedgerId(purchasesForRawMaterialSaveDTO.getBankLedgerId());
        voucherDetailDTOs.add(voucherDetailDTO);
        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);


        if (purchasesForRawMaterialSaveDTO.getVoucherNo() != null && purchasesForRawMaterialSaveDTO.getVoucherNo() != 0) {
            voucherGroupListService.deleteLedgerVoucherDetails(purchasesForRawMaterialSaveDTO.getVoucherNo(), VoucherTypeEnum.PURCHASE.getValue(), currentUser);
//            viewItemDetailDao.deletePurchaseRelatedVoucher(purchaseDTO.getVoucherNo(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
        }

        responseMessage = voucherCreationService.performPurchaseAndSaleVoucherEntry(
                voucherDTO, currentUser);
        return responseMessage;

    }

    public void purchasesForRawMaterialSave(CurrentUser currentUser, PurchasesForRawMaterialSaveDTO purchasesForRawMaterialSaveDTO) {

        for (int i = 0; i < purchasesForRawMaterialSaveDTO.getPurchaseDTOS().size(); i++) {

            RawMaterialPurchasesStorage rawMaterialPurchasesStorage = new RawMaterialPurchasesStorage();

            rawMaterialPurchasesStorage.setPurchaseDate(purchasesForRawMaterialSaveDTO.getPurchaseDate());
            rawMaterialPurchasesStorage.setPurchaseInvoiceNumber(purchasesForRawMaterialSaveDTO.getPurchaseInvoiceNo());

            // This the  Purchase Item List For Same PurchaseInvoiceNumber
            rawMaterialPurchasesStorage.setItemName(purchasesForRawMaterialSaveDTO.getPurchaseDTOS().get(i).getItemName());
            rawMaterialPurchasesStorage.setQty(purchasesForRawMaterialSaveDTO.getPurchaseDTOS().get(i).getQty());
            rawMaterialPurchasesStorage.setPrice(purchasesForRawMaterialSaveDTO.getPurchaseDTOS().get(i).getPrice());
            rawMaterialPurchasesStorage.setTotalAmount(purchasesForRawMaterialSaveDTO.getPurchaseDTOS().get(i).getTotalAmount());
            rawMaterialPurchasesStorage.setUnitId(purchasesForRawMaterialSaveDTO.getPurchaseDTOS().get(i).getUnitId());
            rawMaterialPurchasesStorage.setLocationId(purchasesForRawMaterialSaveDTO.getPurchaseDTOS().get(i).getLocationId());


            rawMaterialPurchasesStorage.setIsCashId(purchasesForRawMaterialSaveDTO.getIsCash());
            if (purchasesForRawMaterialSaveDTO.getIsCash() == 1) {
                rawMaterialPurchasesStorage.setPurchaseUnder("Cash");
                rawMaterialPurchasesStorage.setBankLedgerId("NOT-APPLICABLE");
                rawMaterialPurchasesStorage.setSupplierId("NOT-APPLICABLE");
            }
            if (purchasesForRawMaterialSaveDTO.getIsCash() == 2) {

                rawMaterialPurchasesStorage.setPurchaseUnder("Bank");
                rawMaterialPurchasesStorage.setBankLedgerId(purchasesForRawMaterialSaveDTO.getBankLedgerId());
                rawMaterialPurchasesStorage.setSupplierId("NOT-APPLICABLE");

            }
            if (purchasesForRawMaterialSaveDTO.getIsCash() == 3) {

                rawMaterialPurchasesStorage.setPurchaseUnder("Credit");
                rawMaterialPurchasesStorage.setBankLedgerId(String.valueOf(purchasesForRawMaterialSaveDTO.getSupplierId()));
                rawMaterialPurchasesStorage.setSupplierId("NOT-APPLICABLE");

            }

            rawMaterialPurchasesStorage.setFinancialYearId(currentUser.getFinancialYearId());
            rawMaterialPurchasesStorage.setCompanyId(currentUser.getCompanyId());
            rawMaterialPurchasesStorage.setCreatedBy(currentUser.getLoginId());
            purchasesForRawMaterialDao.save(rawMaterialPurchasesStorage);

        }

    }


    //---------------------------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------------------------

    public ResponseMessage openingBalanceForRawMaterial(RawMaterialStorageDTO rawMaterialStorageDTO,CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        RawMaterialStorage rawMaterialStorage = new RawMaterialStorage();
        if (rawMaterialStorageDTO.getStorageModifier().equals("openingBalance")) {

            rawMaterialStorage.setParticularItemName(rawMaterialStorageDTO.getRawMaterialParticularName());
            rawMaterialStorage.setQuantity(rawMaterialStorageDTO.getRawMaterialParticularQty());
            rawMaterialStorage.setUnitId(rawMaterialStorageDTO.getRawMaterialParticularUnit());
            rawMaterialStorage.setPrice(rawMaterialStorageDTO.getRawMaterialParticularPrice());
            rawMaterialStorage.setLocationId(rawMaterialStorageDTO.getRawMaterialParticularLocation());
            rawMaterialStorage.setOpeningBalanceDate(rawMaterialStorageDTO.getOpenBalanceEntryDate());
            rawMaterialStorage.setStorageModifier(rawMaterialStorageDTO.getStorageModifier());
            rawMaterialStorage.setStorageModifier("OpeningBalance");
            rawMaterialStorage.setCompanyId(currentUser.getCompanyId());
            rawMaterialStorage.setCompanyName(currentUser.getCompanyName());
            responseMessage.setStatus(1);
            rawMaterialStorageDao.save(rawMaterialStorage);
            return  responseMessage;

        }{
            rawMaterialStorage.setStorageModifier("Not-OpeningBalance");
        }
        return  responseMessage;

    }

    //---------------------------------------------------------------------------------------------------------------------

}