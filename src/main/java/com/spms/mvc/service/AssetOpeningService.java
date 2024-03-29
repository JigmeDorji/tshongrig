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
import com.spms.mvc.library.helper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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

    @Autowired
    private AutoVoucherService autoVoucherService;


    @Autowired
    AddItemService addItemService;

    public List<DropdownDTO> getFixedAssetGroupList() {
        return assetOpeningDao.getFixedAssetGroupList();
    }

    //        @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(CurrentUser currentUser, OpeningAndBuyingDTO openingAndBuyingDTO) throws ParseException {

        Integer voucherNo = null;
        Integer voucherTypeId = null;
        BigInteger purchaseMasterId;
        BigInteger purchaseId;
        String voucherNarration = "";
        BigInteger faPurchaseDetailId;


        ResponseMessage responseMessage = new ResponseMessage();
        //valid Business

        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {
            if (ledgerService.getCashLedgerByCashAccountHead(currentUser) == null) {
                responseMessage.setStatus(0);
                responseMessage.setText("There is no cash ledger. Please check.");
                return responseMessage;
            }
        }

        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue())) {
            if (addItemService.checkCashBalance(PaymentModeTypeEnum.CASH.getValue(),
                    openingAndBuyingDTO.getAmtReceived(), currentUser).getStatus() == 0) {
                responseMessage.setStatus(0);
                responseMessage.setText("Cash Insufficient.");
                return responseMessage;
            }
        }

        //Delete the existing data
        if (openingAndBuyingDTO.getFaPurchaseId() != null && openingAndBuyingDTO.getPurchaseMasterId() != null) {
            assetOpeningDao.deleteItemFromDetail(openingAndBuyingDTO.getFaPurchaseId());
            assetOpeningDao.deleteItem(openingAndBuyingDTO.getFaPurchaseId());
            if (assetOpeningDao.isOpeningAssetCount(openingAndBuyingDTO.getPurchaseMasterId())) {
                assetOpeningDao.deleteItemFromMaster(openingAndBuyingDTO.getPurchaseMasterId());
            }
            openingAndBuyingDTO.setPurchaseMasterId(null);
            openingAndBuyingDTO.setFaPurchaseId(null);
        }

        //check party name already exists
        Integer partyId = partyDetail(currentUser, openingAndBuyingDTO.getIsCash(), openingAndBuyingDTO.getPartyName(),
                accSaleInvoiceGenerationDao, openingAndBuyingDTO.getPartyAddress(),
                openingAndBuyingDTO.getPartyContactNo(), openingAndBuyingDTO.getPartyEmail());

        //insert into master
        FaPurchaseMaster faPurchaseMaster = new FaPurchaseMaster();

        if (openingAndBuyingDTO.getPurchaseMasterId() == null) {
            purchaseMasterId = assetOpeningDao.getMaxMasterFaPurchaseId();
            purchaseMasterId = purchaseMasterId == null ? BigInteger.ONE : purchaseMasterId.add(BigInteger.ONE);
        } else {
            purchaseMasterId = openingAndBuyingDTO.getPurchaseMasterId();
        }

        faPurchaseMaster.setPartyId(partyId);
        faPurchaseMaster.setPurchaseMasterId(purchaseMasterId);
        faPurchaseMaster.setAsOnDate(openingAndBuyingDTO.getPurchaseDate());
        faPurchaseMaster.setPurchaseInvoiceNo(openingAndBuyingDTO.getPurchaseInvoiceNo());
        faPurchaseMaster.setPaidInType(openingAndBuyingDTO.getIsCash());
        faPurchaseMaster.setVoucherNo(openingAndBuyingDTO.getVoucherNo());
        faPurchaseMaster.setCompanyId(currentUser.getCompanyId());
        faPurchaseMaster.setVoucherNo(voucherNo);
        faPurchaseMaster.setAmtReceived(openingAndBuyingDTO.getAmtReceived());
        faPurchaseMaster.setCreatedBy(currentUser.getLoginId());
        faPurchaseMaster.setCreatedDate(currentUser.getCreatedDate());
        assetOpeningDao.saveToMasterTable(faPurchaseMaster);

        for (OpeningAndBuyingListDTO openingAndBuyingListDTO : openingAndBuyingDTO.getOpeningAndBuyingListDTO()) {

            //save to main table
            FaPurchase fa_purchase = new FaPurchase();
            if (openingAndBuyingListDTO.getFaPurchaseId() == null) {
                purchaseId = assetOpeningDao.getMaxFaPurchaseId();
                purchaseId = purchaseId == null ? BigInteger.ONE : purchaseId.add(BigInteger.ONE);
            } else {
                purchaseId = openingAndBuyingListDTO.getFaPurchaseId();
            }
            fa_purchase.setFaPurchaseId(purchaseId);
            fa_purchase.setPurchaseMasterId(purchaseMasterId);
            fa_purchase.setDepreciatedValue(openingAndBuyingListDTO.getDepreciatedValue());
            fa_purchase.setOpeningBalance(openingAndBuyingListDTO.getOpeningBalance());
            fa_purchase.setPurchaseDate(openingAndBuyingListDTO.getPurchaseDate() == null ? openingAndBuyingDTO.getPurchaseDate() : openingAndBuyingListDTO.getPurchaseDate());
            fa_purchase.setAssetDetailId(openingAndBuyingListDTO.getAssetDetailId());
            fa_purchase.setRate(openingAndBuyingListDTO.getRate());
            fa_purchase.setQty(openingAndBuyingListDTO.getQty());
            fa_purchase.setCreatedBy(currentUser.getLoginId());
            fa_purchase.setCreatedDate(currentUser.getCreatedDate());
            assetOpeningDao.save(fa_purchase);

            Integer accTypeId = assetOpeningDao.getAccTypeByAssetItem(openingAndBuyingListDTO.getAssetDetailId());

            //Accounting Entry
            int monthStart = 0;
            int selectedMonth = currentUser.getCreatedDate().getMonth();
            int purchaseMonth = openingAndBuyingDTO.getPurchaseInvoiceNo() == null ? 0 : openingAndBuyingDTO.getPurchaseDate().getMonth();

            if (openingAndBuyingDTO.getPurchaseInvoiceNo() == null) {
                //update opening balance
                ledgerService.updateOpeningBalance(ledgerService.getLedgerIdByLedgerName(ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId),
                        currentUser.getCompanyId(), openingAndBuyingListDTO.getOpeningBalance());
            } else {
                monthStart = purchaseMonth;
            }


//            if the purchase date fall within the current year
            if (isPurchaseInCurrentYear(openingAndBuyingListDTO.getPurchaseDate()) || isPurchaseInCurrentYear(openingAndBuyingDTO.getPurchaseDate())) {
                int count = 1;
                if (!isPurchaseInCurrentYear(openingAndBuyingListDTO.getPurchaseDate())){
                    for (int month = purchaseFromMonth(openingAndBuyingDTO.getPurchaseDate()); month <= selectedMonth + 1; month++) {
                        System.out.println(count);
                        count++;

                        Double depreciatedAmount = 0.0;
                        int numOfDays;
                        if (month == purchaseFromMonth(openingAndBuyingDTO.getPurchaseDate())) {
                            numOfDays = calculateTheNumOfDays(openingAndBuyingDTO.getPurchaseDate());

                        } else {
                            numOfDays = numberOfDaysInCurrentMonth(month);
                        }

                        depreciatedAmount = calculateDepreciationAmount(currentUser, accTypeId,
                                openingAndBuyingListDTO.getRate(), month, numOfDays);


//                =====
                        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();
                        VoucherDTO voucherDTO = new VoucherDTO();

                        if (openingAndBuyingDTO.getPurchaseInvoiceNo() == null) {
                            voucherNarration = "Asset opening entry";
                            voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
                            if (openingAndBuyingDTO.getVoucherNo() == null) {
                                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                                        currentUser.getCompanyId(), currentUser.getFinancialYearId());
                            } else {
                                voucherNo = openingAndBuyingDTO.getVoucherNo();
                            }
                        } else {
                            voucherNarration = "Asset Buying entry";

                            if (openingAndBuyingDTO.getVoucherNo() == null) {
                                if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue()) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
                                    voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                                    voucherTypeId = VoucherTypeEnum.PAYMENT.getValue();
                                } else {
                                    voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                                    voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
                                }
                            } else {
                                voucherNo = openingAndBuyingDTO.getVoucherNo();
                            }
                        }


                        voucherDTO.setVoucherTypeId(voucherTypeId);
                        voucherDTO.setNarration(voucherNarration);
                        voucherDTO.setVoucherEntryDate(currentUser.getCreatedDate());

                        //Prepare voucher Entry
                        VoucherDetailDTO voucherDrDTo = new VoucherDetailDTO();
                        voucherDrDTo.setDrcrAmount(AccountingUtil.drAmount(depreciatedAmount));
                        voucherDrDTo.setLedgerId(autoVoucherService.getLedgerId("Depreciation", currentUser,
                                AccountTypeEnum.INDIRECT_COST.getValue()));
                        voucherDetailDTOList.add(voucherDrDTo);

                        VoucherDetailDTO voucherCr = new VoucherDetailDTO();
                        voucherCr.setDrcrAmount(AccountingUtil.crAmount(depreciatedAmount));
                        voucherCr.setLedgerId(autoVoucherService.getLedgerId(ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId));
                        voucherDetailDTOList.add(voucherCr);

                        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set((currentUser.getCreatedDate().getYear() + 1900), month - 1, 1);
                        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                        System.out.println(calendar.getTime());

                        voucherDTO.setVoucherEntryDate(calendar.getTime());
                        voucherDTO.setVoucherNo(voucherNo);

                        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

                    }

                }else {
                    for (int month = purchaseFromMonth(openingAndBuyingListDTO.getPurchaseDate()); month <= selectedMonth + 1; month++) {
                        System.out.println(count);
                        count++;

                        Double depreciatedAmount = 0.0;
                        int numOfDays;
                        if (month == purchaseFromMonth(openingAndBuyingListDTO.getPurchaseDate())) {
                            numOfDays = calculateTheNumOfDays(openingAndBuyingListDTO.getPurchaseDate());

                        } else {
                            numOfDays = numberOfDaysInCurrentMonth(month);
                        }

                        depreciatedAmount = calculateDepreciationAmount(currentUser, accTypeId,
                                openingAndBuyingListDTO.getRate(), month, numOfDays);


//                =====
                        List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();
                        VoucherDTO voucherDTO = new VoucherDTO();

                        if (openingAndBuyingDTO.getPurchaseInvoiceNo() == null) {
                            voucherNarration = "Asset opening entry";
                            voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
                            if (openingAndBuyingDTO.getVoucherNo() == null) {
                                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                                        currentUser.getCompanyId(), currentUser.getFinancialYearId());
                            } else {
                                voucherNo = openingAndBuyingDTO.getVoucherNo();
                            }
                        } else {
                            voucherNarration = "Asset Buying entry";

                            if (openingAndBuyingDTO.getVoucherNo() == null) {
                                if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue()) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
                                    voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                                    voucherTypeId = VoucherTypeEnum.PAYMENT.getValue();
                                } else {
                                    voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                                    voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
                                }
                            } else {
                                voucherNo = openingAndBuyingDTO.getVoucherNo();
                            }
                        }


                        voucherDTO.setVoucherTypeId(voucherTypeId);
                        voucherDTO.setNarration(voucherNarration);
                        voucherDTO.setVoucherEntryDate(currentUser.getCreatedDate());

                        //Prepare voucher Entry
                        VoucherDetailDTO voucherDrDTo = new VoucherDetailDTO();
                        voucherDrDTo.setDrcrAmount(AccountingUtil.drAmount(depreciatedAmount));
                        voucherDrDTo.setLedgerId(autoVoucherService.getLedgerId("Depreciation", currentUser,
                                AccountTypeEnum.INDIRECT_COST.getValue()));
                        voucherDetailDTOList.add(voucherDrDTo);

                        VoucherDetailDTO voucherCr = new VoucherDetailDTO();
                        voucherCr.setDrcrAmount(AccountingUtil.crAmount(depreciatedAmount));
                        voucherCr.setLedgerId(autoVoucherService.getLedgerId(ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId));
                        voucherDetailDTOList.add(voucherCr);

                        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set((currentUser.getCreatedDate().getYear() + 1900), month - 1, 1);
                        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                        System.out.println(calendar.getTime());

                        voucherDTO.setVoucherEntryDate(calendar.getTime());
                        voucherDTO.setVoucherNo(voucherNo);

                        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

                    }

                }

            } else {
                for (int month = monthStart; month <= selectedMonth; month++) {

                    List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();
                    VoucherDTO voucherDTO = new VoucherDTO();

                    if (openingAndBuyingDTO.getPurchaseInvoiceNo() == null) {
                        voucherNarration = "Asset opening entry";
                        voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
                        if (openingAndBuyingDTO.getVoucherNo() == null) {
                            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                                    currentUser.getCompanyId(), currentUser.getFinancialYearId());
                        } else {
                            voucherNo = openingAndBuyingDTO.getVoucherNo();
                        }
                    } else {
                        voucherNarration = "Asset Buying entry";

                        if (openingAndBuyingDTO.getVoucherNo() == null) {
                            if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue()) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
                                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                                voucherTypeId = VoucherTypeEnum.PAYMENT.getValue();
                            } else {
                                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                                voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
                            }
                        } else {
                            voucherNo = openingAndBuyingDTO.getVoucherNo();
                        }
                    }

                    Double depreciatedAmount = calculateDepreciationAmount(currentUser, accTypeId,
                            openingAndBuyingListDTO.getRate(), month, numberOfDaysInCurrentMonth(month + 1));

                    voucherDTO.setVoucherTypeId(voucherTypeId);
                    voucherDTO.setNarration(voucherNarration);
                    voucherDTO.setVoucherEntryDate(currentUser.getCreatedDate());

                    //Prepare voucher Entry
                    VoucherDetailDTO voucherDrDTo = new VoucherDetailDTO();
                    voucherDrDTo.setDrcrAmount(AccountingUtil.drAmount(depreciatedAmount));
                    voucherDrDTo.setLedgerId(autoVoucherService.getLedgerId("Depreciation", currentUser,
                            AccountTypeEnum.INDIRECT_COST.getValue()));
                    voucherDetailDTOList.add(voucherDrDTo);

                    VoucherDetailDTO voucherCr = new VoucherDetailDTO();
                    voucherCr.setDrcrAmount(AccountingUtil.crAmount(depreciatedAmount));
                    voucherCr.setLedgerId(autoVoucherService.getLedgerId(ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId));
                    voucherDetailDTOList.add(voucherCr);

                    voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set((currentUser.getCreatedDate().getYear() + 1900), month, 1);
                    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

                    voucherDTO.setVoucherEntryDate(calendar.getTime());
                    voucherDTO.setVoucherNo(voucherNo);

                    voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
                }
            }

//            voucherEntry(openingAndBuyingListDTO, openingAndBuyingDTO,currentUser, voucherNarration, voucherTypeId, voucherNo, accTypeId, selectedMonth);


//                //====
//            for (int month = monthStart; month <= selectedMonth; month++) {
//
//                List<VoucherDetailDTO> voucherDetailDTOList = new ArrayList<>();
//                VoucherDTO voucherDTO = new VoucherDTO();
//
//                if (openingAndBuyingDTO.getPurchaseInvoiceNo() == null) {
//                    voucherNarration = "Asset opening entry";
//                    voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
//                    if (openingAndBuyingDTO.getVoucherNo() == null) {
//                        voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
//                                currentUser.getCompanyId(), currentUser.getFinancialYearId());
//                    } else {
//                        voucherNo = openingAndBuyingDTO.getVoucherNo();
//                    }
//                } else {
//                    voucherNarration = "Asset Buying entry";
//
//                    if (openingAndBuyingDTO.getVoucherNo() == null) {
//                        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue()) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
//                            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
//                            voucherTypeId = VoucherTypeEnum.PAYMENT.getValue();
//                        } else {
//                            voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
//                            voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
//                        }
//                    } else {
//                        voucherNo = openingAndBuyingDTO.getVoucherNo();
//                    }
//                }
//
//                Double depreciatedAmount = calculateDepreciationAmount(currentUser, accTypeId,
//                        openingAndBuyingListDTO.getRate(), month);
//
//                voucherDTO.setVoucherTypeId(voucherTypeId);
//                voucherDTO.setNarration(voucherNarration);
//                voucherDTO.setVoucherEntryDate(currentUser.getCreatedDate());
//
//                //Prepare voucher Entry
//                VoucherDetailDTO voucherDrDTo = new VoucherDetailDTO();
//                voucherDrDTo.setDrcrAmount(AccountingUtil.drAmount(depreciatedAmount));
//                voucherDrDTo.setLedgerId(autoVoucherService.getLedgerId("Depreciation", currentUser,
//                        AccountTypeEnum.INDIRECT_COST.getValue()));
//                voucherDetailDTOList.add(voucherDrDTo);
//
//                VoucherDetailDTO voucherCr = new VoucherDetailDTO();
//                voucherCr.setDrcrAmount(AccountingUtil.crAmount(depreciatedAmount));
//                voucherCr.setLedgerId(autoVoucherService.getLedgerId(ledgerService.getAccountTypeNameByAccType(accTypeId), currentUser, accTypeId));
//                voucherDetailDTOList.add(voucherCr);
//
//                voucherDTO.setVoucherDetailDTOList(voucherDetailDTOList);
//                Calendar calendar = Calendar.getInstance();
//                calendar.set((currentUser.getCreatedDate().getYear() + 1900), month, 1);
//                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
//
//                voucherDTO.setVoucherEntryDate(calendar.getTime());
//                voucherDTO.setVoucherNo(voucherNo);
//
//                voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
//            }
//            //
//            // ===


            //save to detail table
            BigDecimal initialQty = openingAndBuyingListDTO.getQty();
            while (initialQty.compareTo(BigDecimal.ZERO) > 0) {

                FaPurchaseDetail faPurchaseDetail = new FaPurchaseDetail();

                if (openingAndBuyingListDTO.getFaPurchaseDetailId() == null) {
                    faPurchaseDetailId = assetOpeningDao.getMaxFaPurchaseDetailId();
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

        //Accounting entry for the 
        if (openingAndBuyingDTO.getPurchaseInvoiceNo() != null) {
            performAccounting(openingAndBuyingDTO, currentUser);
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Successfully saved.");
        return responseMessage;

    }


    public static Integer partyDetail(CurrentUser currentUser, Integer isCash, String partyName, AccSaleInvoiceGenerationDao accSaleInvoiceGenerationDao, String partyAddress, String partyContactNo, String partyEmail) {
        Integer partyId = null;

        if (Objects.equals(isCash, PaymentModeTypeEnum.CREDIT.getValue()) || (partyName != null && !partyName.equals(""))) { //applies to only party

            partyId = accSaleInvoiceGenerationDao.getPartyIdIFExists(currentUser.getCompanyId(), partyName);

            if (partyId == null) {
                PartyDetail partyDetail = new PartyDetail();
                partyId = accSaleInvoiceGenerationDao.getMaxPartyId() + 1;
                partyDetail.setPartyId(partyId);
                partyDetail.setPartyName(partyName);
                partyDetail.setPartyAddress(partyAddress);
                partyDetail.setPartyContactNo(partyContactNo);
                partyDetail.setPartyEmail(partyEmail);
                partyDetail.setCompanyId(currentUser.getCompanyId());
                partyDetail.setSetDate(currentUser.getCreatedDate());
                partyDetail.setCreatedBy(currentUser.getLoginId());
                accSaleInvoiceGenerationDao.savePartyDetail(partyDetail);
            }
        }
        return partyId;
    }

    public Double calculateDepreciationAmount(CurrentUser currentUser, Integer accTypeId, Double rate, int month, int totalNoOfDaysDif) throws ParseException {

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(2);

        Double depreciationRate = accTypeId.equals(AccountTypeEnum.BUILDING_AND_AMENITIES.getValue()) ? 0.03 : 0.15;
        Calendar cal = Calendar.getInstance();

        cal.setTime(DateUtil.toDate(currentUser.getFinancialYearFrom()));
        int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
//        int totalNoOfDaysDif = numberOfDaysInMonth(month, currentUser.getCreatedDate().getYear());

//        double decAmount = ;
//        System.out.println(decAmount);
        return ((rate * depreciationRate) / numOfDays) * totalNoOfDaysDif;

//        return Double.parseDouble(numberFormat.format(((rate * depreciationRate) / numOfDays) * totalNoOfDaysDif));
    }

    public static int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
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


        Integer voucherNo = null;
        Integer voucherTypeId = null;

        //region cr voucher preparation
        VoucherDTO voucherDTO = new VoucherDTO();
        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();

        if (openingAndBuyingDTO.getVoucherNo() == null) {
            if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CASH.getValue()) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {

                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                voucherTypeId = VoucherTypeEnum.PAYMENT.getValue();

            } else {
                voucherNo = voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(), currentUser.getCompanyId(), currentUser.getFinancialYearId());
                voucherTypeId = VoucherTypeEnum.JOURNAL.getValue();
            }
        } else {
            voucherNo = openingAndBuyingDTO.getVoucherNo();
        }

        voucherDTO.setVoucherTypeId(voucherTypeId);
        voucherDTO.setNarration("Buying fix asset");
        voucherDTO.setVoucherEntryDate(openingAndBuyingDTO.getPurchaseDate());
        voucherDTO.setVoucherNo(voucherNo);

        List<OpeningAndBuyingDTO> openingAndBuyingDTOList = assetOpeningDao.getInvoiceDetailList(openingAndBuyingDTO.getPurchaseInvoiceNo(),
                currentUser.getCompanyId());

        assetOpeningDao.updateVoucherNoByInvoiceNo(openingAndBuyingDTO.getPurchaseInvoiceNo(), voucherNo);

        for (OpeningAndBuyingDTO openingAndBuyingDTOI : openingAndBuyingDTOList) {

            String ledgerId = ledgerService.getLedgerIdByLedgerName(ledgerService.getAccountTypeNameByAccType(openingAndBuyingDTOI.getAccTypeId()),
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

//            if (openingAndBuyingDTO.getAmtReturn() < 0) {
//                amount = openingAndBuyingDTO.getAmtReceived();
//            } else {
            amount = openingAndBuyingDTO.getAmount();
//            }
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(amount));
            voucherDetailDTO.setIsCash(PaymentModeTypeEnum.CASH.getValue());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //Sale in Bank
        if (Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.BANK.getValue())) {
            amount = openingAndBuyingDTO.getAmount();
            voucherDetailDTO = new VoucherDetailDTO();
            voucherDetailDTO.setBankLedgerId(openingAndBuyingDTO.getBankLedgerId());
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(amount));
            voucherDetailDTO.setIsCash(openingAndBuyingDTO.getIsCash());
            voucherDetailDTOs.add(voucherDetailDTO);
        }

        //Sale in credit
            /* Whether the sale is made either from cash or bank, if there is remaining amount left,
             system will take remaining amount as credit for particular party*/

        if ((openingAndBuyingDTO.getPartyName() != null && !openingAndBuyingDTO.getPartyName().equals("")) || Objects.equals(openingAndBuyingDTO.getIsCash(), PaymentModeTypeEnum.CREDIT.getValue())) { // for credit party ledger
            //create ledger for party
            double creditAmount;
            voucherDetailDTO = new VoucherDetailDTO();

            voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(openingAndBuyingDTO.getPartyName(), currentUser, AccountTypeEnum.PAYABLE.getValue()));

            creditAmount = openingAndBuyingDTO.getAmount();
            voucherDetailDTO.setDrcrAmount(AccountingUtil.crAmount(creditAmount));
            voucherDetailDTO.setIsCash(openingAndBuyingDTO.getIsCash());
            voucherDetailDTOs.add(voucherDetailDTO);
        }
        //endregion

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
    }

    public List<OpeningAndBuyingDTO> loadAssetOpeningList(BigInteger faPurchaseId) {
        return assetOpeningDao.loadAssetOpeningList(faPurchaseId);
    }

    public ResponseMessage deleteItem(BigInteger faPurchaseId, CurrentUser currentUser) {

        ResponseMessage responseMessage = new ResponseMessage();
        OpeningAndBuyingDTO openingAndBuyingDTO = assetOpeningDao.checkIsOpening(faPurchaseId);

        if (openingAndBuyingDTO != null) {
            ledgerService.subtractLedgerAmount(ledgerService.getLedgerIdByLedgerName(ledgerService.getAccountTypeNameByAccType(openingAndBuyingDTO.getAccTypeId()), currentUser, openingAndBuyingDTO.getAccTypeId()), currentUser.getCompanyId(), openingAndBuyingDTO.getAmount());
        }

        assetOpeningDao.deleteItemFromDetail(faPurchaseId);
        assetOpeningDao.deleteItem(faPurchaseId);

        responseMessage.setStatus(1);
        responseMessage.setText("You have delete successfully");
        return responseMessage;
    }

    public List<OpeningAndBuyingDTO> loadAssetBuyingList(Integer voucherNo, BigInteger purchaseMasterId) {
        return assetOpeningDao.loadAssetBuyingList(voucherNo, purchaseMasterId);
    }

    //    ======================================================================
    public static boolean isPurchaseInCurrentYear(Date purchaseDate) {
        if (purchaseDate==null){
            return false;
        }else {
            return getYearFromDate(purchaseDate) == getYearFromDate(new Date());
        }
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    public static int calculateTheNumOfDays(Date purchaseDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseDate);
        // Extract the target year, month, and day
        int targetYear = calendar.get(Calendar.YEAR);
        int targetMonth = calendar.get(Calendar.MONTH) + 1; // Months are 0-based, so add 1
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Create the target date with the assigned values
        LocalDate date = LocalDate.of(targetYear, targetMonth, targetDay);
        // Get the end of the month for the given date
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        // Calculate the number of days from the given date to the end of the month
        int daysToEndOfMonth = (date.lengthOfMonth() - date.getDayOfMonth()) + 1;
        System.out.println("Number of days from " + date + " to the end of the month: " + daysToEndOfMonth);
        return daysToEndOfMonth;
    }


    public static int purchaseFromMonth(Date purchaseDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseDate);
        // Create a YearMonth instance for the specified year and month
        return calendar.get(Calendar.MONTH) + 1;
    }


//    public static void numberOfDaysInMonth(int month, int year) {
//
//        // Create a Calendar instance and set it to the Date object
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), month);
//        // Get the number of days in the specified month
//        int daysInMonth = yearMonth.lengthOfMonth();
//        System.out.println("Number of days in " + year + "-" + month + ": " + daysInMonth);
//    }

    public static int numberOfDaysInCurrentMonth(int month) {
        // Create a Calendar instance and set it to the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), month);
        // Get the number of days in the specified month
        return yearMonth.lengthOfMonth();

    }

    public static double formattedDoubleValue(double rawCurrentDepC) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        return Double.parseDouble(decimalFormat.format(rawCurrentDepC));
    }

    public static double formattedDoubleValues(double rawCurrentDepC) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return Double.parseDouble(decimalFormat.format(rawCurrentDepC));
    }

    //    ================================================================
    private void voucherEntry(OpeningAndBuyingListDTO openingAndB,
                              CurrentUser currentUser,
                              String voucherNarration,
                              Integer voucherTypeId,
                              Integer voucherNo,
                              Integer accTypeId,
                              Integer selectedMonth) throws ParseException {


//        for (int month = 0; month <= selectedMonth; month++) {
//
//        }
    }

}