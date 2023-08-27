

package com.spms.mvc.service;

import com.spms.mvc.Enumeration.*;
import com.spms.mvc.dao.AddItemDao;
import com.spms.mvc.dao.ViewItemDetailDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.BrandWiseItemCode;
import com.spms.mvc.entity.Purchase;
import com.spms.mvc.entity.PurchaseCreditSupplierDetail;
import com.spms.mvc.entity.Purchase_A;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by SonamPC on 13-Dec-16.
 */
@Service("addItemService")
public class AddItemService {
    ResponseMessage responseMessage = new ResponseMessage();
    @Autowired
    private AddItemDao addItemDao;

    @Autowired
    private LedgerService ledgerService;
    @Autowired
    private VoucherGroupListService voucherGroupListService;

    @Autowired
    private SupplierSetupService supplierSetupService;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private BrandWiseItemCode brandWiseItemCode;

    @Autowired
    private SaleItemService saleItemService;

    @Autowired
    private ViewItemDetailDao viewItemDetailDao;


    public List<DropdownDTO> getItemCategory() {
        return addItemDao.getItemCategory();
    }

    public List<DropdownDTO> getItemNameList() {
        return addItemDao.getItemNameList();
    }

    //    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage save(PurchaseCallingDTO purchaseCallingDTO, CurrentUser currentUser) throws ParseException {

        ResponseMessage responseMessage = new ResponseMessage();

        /*validate item name is exist or not*/
       /* for (PurchaseDTO purchaseDTO : purchaseCallingDTO.getPurchaseDTOS()) {
            if (addItemDao.checkIsItemNameExist(purchaseDTO.getItemName(), currentUser, purchaseDTO.getItemCode())) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Item name '" + purchaseDTO.getItemName() + "' already exist. Please select existing item '" + purchaseDTO.getItemName() + "' and add again.");
                return responseMessage;
            }
        }*/

        //check for cash availability
//        Double txnAmount = purchaseDTO.getCostPrice() * purchaseDTO.getQty();
        Double totalCashAmount = 0.0;
        Double totalCashOutFlow = 0.0;
        try {
            if (purchaseCallingDTO.getIsCash() == 1) {//Checking cash is there or not.
                if (addItemDao.isCashLedgerExist(currentUser, AccountTypeEnum.CASH.getValue())) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("No cash ledger.");
                    return responseMessage;
                }
            }
            VoucherDTO voucherDTO = new VoucherDTO();
            if (purchaseCallingDTO.getIsOpeningEntry() == 'N' ) {
                //region accounting
                if (purchaseCallingDTO.getIsCash().equals(PaymentModeTypeEnum.CASH.getValue())) {
                    if (checkCashBalance(purchaseCallingDTO.getIsCash(), purchaseCallingDTO.getTotalTranAmount(), currentUser).getStatus() == 0) {
                        responseMessage.setStatus(0);
                        responseMessage.setText("Insufficient cash balance.");
                        return responseMessage;
                    }
//            LedgerDTO ledgerDTO = voucherGroupListService.getOpeningBalance(ledgerService.getLedgerIdByAccountTypeId(AccountTypeEnum.CASH.getValue()), currentUser);
//
//            totalCashAmount = addItemDao.getTotalCash(AccountTypeEnum.CASH.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
//            totalCashOutFlow = addItemDao.getTotalCashOutFlow(AccountTypeEnum.CASH.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
//            totalCashOutFlow = totalCashOutFlow == null ? 0.0 : totalCashOutFlow;
//            totalCashAmount = totalCashAmount == null ? 0.0 + Math.abs(ledgerDTO.getOpeningBal()) : (Math.abs(totalCashAmount) + Math.abs(ledgerDTO.getOpeningBal())) - totalCashOutFlow;
//
//            if (totalCashAmount < txnAmount) {
//                responseMessage.setText("Insufficient cash balance.");
//                responseMessage.setStatus(0);
//                return responseMessage;
//            }
                }
                voucherDTO.setVoucherTypeId(AccountTypeEnum.EQUITY_OR_CAPITAL.getValue());
                voucherDTO.setVoucherEntryDate(purchaseCallingDTO.getPurchaseDate());
                if (purchaseCallingDTO.getVoucherNo() != null) {
                    voucherDTO.setVoucherNo(purchaseCallingDTO.getVoucherNo());
                } else {
                    voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(
                            AccountTypeEnum.EQUITY_OR_CAPITAL.getValue(),
                            currentUser.getCompanyId(), currentUser.getFinancialYearId()));
                }

                voucherDTO.setNarration("Purchase Entry");

                List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
                //Dr Purchase
                VoucherDetailDTO purchaseDto = new VoucherDetailDTO();
                purchaseDto.setDrcrAmount(AccountingUtil.drAmount(purchaseCallingDTO.getTotalTranAmount()));
                if (currentUser.getBusinessType().equals(BusinessType.Construction.getValue())) {
                    purchaseDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.MATERIAL.getText(),
                            currentUser, AccountTypeEnum.MATERIAL.getValue()));
                } else {
                    purchaseDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(AccountTypeEnum.PURCHASE.getText(),
                            currentUser, AccountTypeEnum.PURCHASE.getValue()));
                }


                purchaseDto.setIsCash(purchaseCallingDTO.getIsCash());
                voucherDetailDTOs.add(purchaseDto);


                //cr bank/cash/Party
                VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
                if (purchaseCallingDTO.getSupplierId() != null) {
                    voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(purchaseCallingDTO.getSupplierName(),
                            currentUser, AccountTypeEnum.PAYABLE.getValue()));
                }
                voucherDetailDTO.setIsCash(purchaseCallingDTO.getIsCash());
                voucherDetailDTO.setBankLedgerId(purchaseCallingDTO.getBankLedgerId());
                voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(purchaseCallingDTO.getTotalTranAmount()));
                voucherDetailDTO.setBankLedgerId(purchaseCallingDTO.getBankLedgerId());
                voucherDetailDTOs.add(voucherDetailDTO);
                voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);


                if (purchaseCallingDTO.getVoucherNo() != null && purchaseCallingDTO.getVoucherNo() != 0) {
                    voucherGroupListService.deleteLedgerVoucherDetails(purchaseCallingDTO.getVoucherNo(), VoucherTypeEnum.PURCHASE.getValue(), currentUser);
//            viewItemDetailDao.deletePurchaseRelatedVoucher(purchaseDTO.getVoucherNo(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                }


                responseMessage = voucherCreationService.performPurchaseAndSaleVoucherEntry(
                        voucherDTO, currentUser);
                if (responseMessage.getStatus() == 0) {
                    return responseMessage;
                }
                //endregion


            } else {

                voucherDTO.setVoucherNo(0);
                purchaseCallingDTO.setIsCash(0);
                if (purchaseCallingDTO.getPurchaseDTOS().get(0).getPurchaseId() == null) {
                    String maxSerialNo = addItemDao.getMaxSerialIDByBrand(purchaseCallingDTO.getPurchaseDTOS().get(0).getBrandId(), currentUser.getCompanyId());
                    addItemDao.updateBrandWiseSerialNo(purchaseCallingDTO.getPurchaseDTOS().get(0).getBrandId(),
                            (Integer.parseInt(maxSerialNo) + 1), currentUser.getCompanyId());
                }
            }

            for (PurchaseDTO purchaseDTO : purchaseCallingDTO.getPurchaseDTOS()) {
                Integer brandId;

                if (purchaseCallingDTO.getIsOpeningEntry() == 'N') {
                    brandId = addItemDao.getBrandIdByName(purchaseDTO.getBrandName(), currentUser.getCompanyId());
                } else {
                    brandId = purchaseCallingDTO.getPurchaseDTOS().get(0).getBrandId();
                }
                String currentItemCode = purchaseDTO.getItemCode();
                Purchase purchase = new Purchase();
                BigDecimal currentQty = BigDecimal.ZERO;
                if (purchaseDTO.getPurchaseId() != null) {
                    purchase.setPurchaseId(purchaseDTO.getPurchaseId());
                    purchase.setItemCode(purchaseDTO.getItemCode());
                    BigDecimal qty = purchaseDTO.getQty();
                    currentQty = addItemDao.getCurrentQty(purchaseDTO.getItemCode(), currentUser.getCompanyId());
                    currentQty = currentQty == null ? BigDecimal.ZERO : currentQty;
                    if (purchaseDTO.getVoucherNo() != null) {
                        BigDecimal previousReceivedQTy = addItemDao.getPreviousReceivedQty(currentUser, purchaseDTO.getVoucherNo(), purchaseDTO.getItemCode());
                        currentQty = currentQty.subtract(previousReceivedQTy);
                    }
                    purchase.setQty(currentQty.add(qty));
                } else {
                    purchase.setItemCode(currentItemCode);
                    purchase.setQty(purchaseDTO.getQty());
                }
                purchase.setUnitId(purchaseDTO.getUnitId());
                purchase.setBrandId(brandId);
                purchase.setPartNo(purchaseDTO.getPartNo());
                purchase.setPurchaseDate(purchaseCallingDTO.getPurchaseDate());
                purchase.setPurchaseInvoiceNo(purchaseCallingDTO.getPurchaseInvoiceNo());
                purchase.setCostPrice(purchaseDTO.getCostPrice());
                purchase.setSellingPrice(purchaseDTO.getSellingPrice());
                purchase.setItemName(purchaseDTO.getItemName());
                //            if (purchaseDTO.getPurchaseId() == null) {
                //                purchase.setItemName(purchaseDTO.getType() + " : " + purchaseDTO.getItemName());
                //            } else {
                //                purchase.setItemName(purchaseDTO.getItemName());
                //            }
                purchase.setLocationId(purchaseDTO.getLocationId());
                purchase.setCompanyId(currentUser.getCompanyId());
                purchase.setFinancialYearId(currentUser.getFinancialYearId());
                purchase.setSetDate(new Date());
                purchase.setType(purchaseDTO.getType());
                purchase.setCreatedBy(currentUser.getLoginId());
                Integer purchaseId = addItemDao.savePurchaseItem(purchase);
                //            if (purchaseDTO.getPurchaseId() == null) {
                //                String maxSerialNo = addItemDao.getMaxSerialIDByBrand(brandId, currentUser.getCompanyId());
                //                addItemDao.updateBrandWiseSerialNo(brandId,
                //                        (Integer.parseInt(maxSerialNo) + 1), currentUser.getCompanyId());
                //            }

                /**
                 * save to Audit
                 */
                Purchase_A purchase_a = new Purchase_A();
                if (purchaseCallingDTO.getVoucherNo() != null) {
                    purchase_a.setId(addItemDao.getAuditIdByVoucherNo(purchaseDTO.getPurchaseId(), purchaseCallingDTO.getVoucherNo()));
                }
                purchase_a.setPurchaseId(purchaseId);
                purchase_a.setQty(purchaseDTO.getQty());
                purchase_a.setUnitId(purchaseDTO.getUnitId());
                purchase_a.setSetDate(purchase.getPurchaseDate());
                purchase_a.setCreatedBy(currentUser.getLoginId());
                purchase_a.setPurchaseInvoiceNo(purchase.getPurchaseInvoiceNo());
                purchase_a.setCostPrice(purchase.getCostPrice());
                purchase_a.setSellingPrice(purchase.getSellingPrice());
                purchase_a.setType(purchase.getType());
                purchase_a.setItemCode(purchase.getItemCode());
                purchase_a.setPurchaseDate(purchase.getPurchaseDate());
                purchase_a.setPurchaseVoucherNo(voucherDTO.getVoucherNo());
                purchase_a.setIsCash(purchaseCallingDTO.getIsCash());
                purchase_a.setPartNo(purchase.getPartNo());
                purchase_a.setLocationId(purchase.getLocationId());
                purchase_a.setCompanyId(purchase.getCompanyId());
                purchase_a.setFinancialYearId(purchase.getFinancialYearId());
                purchase_a.setItemName(purchase.getItemName());
                purchase_a.setBrandId(brandId);
                if (purchaseDTO.getQty().equals(BigDecimal.ZERO)) {
                    purchase_a.setCmdFlag('D');
                } else {
                    purchase_a.setCmdFlag('C');
                }
                addItemDao.saveToAuditTable(purchase_a);
            }

            //save supplier vs purchase details if it is credit purchase
            if (Objects.equals(purchaseCallingDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())) {
                PurchaseCreditSupplierDetail purchaseCreditSupplierDetail = new PurchaseCreditSupplierDetail();
//            purchaseCreditSupplierDetail.setPurchaseId(purchaseId);
                purchaseCreditSupplierDetail.setPurchaseInvoiceNo(purchaseCallingDTO.getPurchaseInvoiceNo());
                purchaseCreditSupplierDetail.setSupplierId(purchaseCallingDTO.getSupplierId());
                addItemDao.savePurchaseCreditSupplierDetail(purchaseCreditSupplierDetail);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();

            responseMessage.setStatus(0);
            responseMessage.setText("Error while saving.");
            return responseMessage;
        }

        responseMessage.setStatus(1);
        responseMessage.setText("You have successfully saved.");
        return responseMessage;

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
            totalCashAmount = addItemDao.getTotalCash(AccountTypeEnum.CASH.getValue(),
                    currentUser.getCompanyId(),
                    currentUser.getFinancialYearId());

            totalCashOutFlow = addItemDao.getTotalCashOutFlow(ledgerId,
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

    public ResponseMessage getPurchaseDetail(Integer purchaseId, Date purchaseDate, CurrentUser currentUser, Integer voucherNo) {
        PurchaseDTO purchaseDTO = addItemDao.getPurchaseDetail(purchaseId, purchaseDate, voucherNo);
        return getItemListByInvoiceNo(purchaseDTO.getPurchaseInvoiceNo(), currentUser);
        /*String purchaseLedgerId = ledgerService.getLedgerIdByLedgerName(LedgerType.PURCHASE.getText(), currentUser, AccountTypeEnum.PURCHASE.getValue());
        String ledgerId = addItemDao.getPurchaseInLedgerId(purchaseLedgerId, voucherNo, currentUser);

        if (purchaseDTO.getIsCash() == 2) {
            purchaseDTO.setBankLedgerId(ledgerId);
        }
        if (purchaseDTO.getIsCash() == 3) {
            String ledgerName = ledgerService.getNameByLedgerId(ledgerId,
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            purchaseDTO.setSupplierId(supplierSetupService.getSupplierIdByName(ledgerName, currentUser));
            purchaseDTO.setSupplierName(ledgerName);
        }
        purchaseDTO.setQty(purchaseDTO.getSumQty().intValue());
//        purchaseDTO.setStringPurchaseDate(DateUtil.format(purchaseDTO.getPurchaseDate(), DateUtil.YYYY_MM_DD));
        return purchaseDTO;*/
    }

    public PurchaseDTO getOpeningPurchaseDetail(Integer purchaseId, Date purchaseDate, Integer voucherNo) {
        return addItemDao.getPurchaseDetail(purchaseId, purchaseDate, voucherNo);
    }

    public SaleItemDTO getItemInfo(Integer itemId) {
        return addItemDao.getItemInfo(itemId);
    }

    public SaleItemDTO getItemDetailsOnSearchByName(Integer itemId) {
        return addItemDao.getItemDetailsOnSearchByName(itemId);
    }

    public ResponseMessage checkItemName(String itemName) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (addItemDao.checkItemName(itemName)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Item name already exists. Please try with different name.");
        } else {
            responseMessage.setStatus(1);
        }
        return responseMessage;
    }

    public PurchaseDTO getRecentPurchaseData() {
        return addItemDao.getRecentPurchaseData();
    }

    public List<DropdownDTO> getBrandList(CurrentUser currentUser) {
        return addItemDao.getBrandList(currentUser.getCompanyId());
    }

    public PurchaseDTO getSlNo(Integer brandId) {
//        addItemDao.getSlNoForTradingBusinessType();
        PurchaseDTO purchaseDTO = addItemDao.getSlNo(brandId);
        Integer itemCode = Integer.parseInt(purchaseDTO.getSerialNo());
        purchaseDTO.setItemCode(purchaseDTO.getPrefixCode() + itemCode.toString());
        return purchaseDTO;

    }

    public PurchaseDTO getItemDetails(String itemCode, CurrentUser currentUser) {
        return addItemDao.getItemDetails(itemCode, currentUser.getCompanyId());
    }

    public ResponseMessage saveBrandDetail(BrandDTO brandDTO, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (!addItemDao.checkInBrandExists(brandDTO.getBrandName(), currentUser.getCompanyId())) {
            responseMessage.setStatus(0);
            responseMessage.setText("Brand already exists. Please try with different brand.");
            return responseMessage;
        }

        brandWiseItemCode.setBrandId(addItemDao.getMaxBrandId() + 1);
        brandWiseItemCode.setBrandName(brandDTO.getBrandName());
        brandWiseItemCode.setBrandPrefix(brandDTO.getBrandPrefix());
        brandWiseItemCode.setSerialNo(addItemDao.getCompanyWiseSerialNo(brandDTO.getBrandId()) + 1);
        brandWiseItemCode.setRemarks(brandDTO.getRemarks());
        brandWiseItemCode.setCompanyId(currentUser.getCompanyId());
        brandWiseItemCode.setSetDate(currentUser.getCreatedDate());
        brandWiseItemCode.setCreatedBy(currentUser.getLoginId());
        Integer brandId = addItemDao.saveBrandDetail(brandWiseItemCode);
        BrandDTO resBrandDTO = new BrandDTO();
        resBrandDTO.setBrandId(brandId);
        responseMessage.setStatus(1);
        responseMessage.setDTO(resBrandDTO);
        responseMessage.setText("Brand successfully saved");
        return responseMessage;
    }

    public PurchaseDTO getItemDetailsByPartNo(String partNo,Integer companyId) {
        return addItemDao.getItemDetailsByPartNo(partNo,companyId);
    }

    public List<DropdownDTO> getTypeDetail(CurrentUser currentUser) {
        return addItemDao.getTypeDetail(currentUser.getCompanyId());
    }

    public List<DropdownDTO> getItemCodeListByBrandId(CurrentUser currentUser, Integer brandId) {
        return addItemDao.getItemCodeListByBrandId(currentUser, brandId);
    }

    public ResponseMessage checkPurchaseInvoiceNoAlreadyEntered(String itemCode, String purchaseInvoiceNo) {


        if (addItemDao.checkPurchaseInvoiceNoAlreadyEntered(itemCode, purchaseInvoiceNo)) {
            responseMessage.setStatus(0);
            responseMessage.setText("You cannot enter multiple purchase invoice no. for same item.");
        } else {
            responseMessage.setStatus(1);
        }

        return responseMessage;
    }

    public ResponseMessage getItemListByInvoiceNo(String purchaseInvoiceNo, CurrentUser currentUser) {
        responseMessage = new ResponseMessage();
        List<PurchaseDTO> purchaseDTOS = addItemDao.getItemListByInvoiceNo(purchaseInvoiceNo, currentUser.getCompanyId());
        if (purchaseDTOS.size() == 0) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("No purchase detail found for the invoice no:" + purchaseInvoiceNo);
            return responseMessage;
        }
        String purchaseLedgerId;
        if (currentUser.getBusinessType().equals(BusinessType.Construction.getValue())) {
            purchaseLedgerId = ledgerService.getLedgerIdByLedgerName(LedgerType.MATERIAL.getText(), currentUser, AccountTypeEnum.MATERIAL.getValue());
        } else {
            purchaseLedgerId = ledgerService.getLedgerIdByLedgerName(LedgerType.PURCHASE.getText(), currentUser, AccountTypeEnum.PURCHASE.getValue());
        }
        String ledgerId = addItemDao.getPurchaseInLedgerId(purchaseLedgerId, purchaseDTOS.get(0).getPurchaseVoucherNo(), currentUser);

        if (purchaseDTOS.get(0).getIsCash() == 2) {
            purchaseDTOS.get(0).setBankLedgerId(ledgerId);
        }
        if (purchaseDTOS.get(0).getIsCash() == 3) {
            String ledgerName = ledgerService.getNameByLedgerId(ledgerId,
                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
            purchaseDTOS.get(0).setSupplierId(supplierSetupService.getSupplierIdByName(ledgerName, currentUser));
            purchaseDTOS.get(0).setSupplierName(ledgerName);
        }
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setDTO(purchaseDTOS);
        return responseMessage;
    }

    public ResponseMessage updateItemSerialNumber(String brandName, CurrentUser currentUser) {
        Integer brandId = addItemDao.getBrandIdByName(brandName, currentUser.getCompanyId());
        String maxSerialNo = addItemDao.getMaxSerialIDByBrand(brandId, currentUser.getCompanyId());
        addItemDao.updateBrandWiseSerialNo(brandId,
                (Integer.parseInt(maxSerialNo) + 1), currentUser.getCompanyId());
        responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public PurchaseDTO getPurchaseDetailByVoucherNo(Integer voucherNo, CurrentUser currentUser) {
        return addItemDao.getPurchaseDetailByVoucherNo(voucherNo, currentUser);
    }

    public ResponseMessage deleteAllThePurchaseEntry(Integer purchaseVoucherNo, CurrentUser currentUser, String purchaseInvoiceNo) {
        addItemDao.deleteAllThePurchaseEntry(purchaseInvoiceNo, currentUser.getCompanyId());
        return voucherGroupListService.deleteLedgerVoucherDetails(purchaseVoucherNo, VoucherTypeEnum.PURCHASE.getValue(), currentUser);
    }

    public List<DropdownDTO> getUnitList() {
        return addItemDao.getUnitList();
    }


    public Integer getSINo(CurrentUser currentUser) {
        String SiNo= String.valueOf(addItemDao.getSINo(currentUser.getCompanyId()));
        return Integer.valueOf(SiNo);
    }
}
