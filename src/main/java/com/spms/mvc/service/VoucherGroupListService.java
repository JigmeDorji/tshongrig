package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dao.VoucherCreationDao;
import com.spms.mvc.dao.VoucherGroupListDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.BankReconciliation;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/19/2019.
 */
@Service("voucherGroupListService")
public class VoucherGroupListService {

    @Autowired
    private VoucherGroupListDao voucherGroupListDao;

    @Autowired
    private FinancialPositionService financialPositionService;

    @Autowired
    private FinancialYearSetupService financialYearSetupService;

    @Autowired
    VoucherCreationDao voucherCreationDao;

    @Autowired
    private AccProfitAndLossReportService accProfitAndLossReportService;

    public List<AccProfitAndLossReportDTO> getVoucherDetailsByLedgerId(String ledgerId, Date fromDate, Date toDate, CurrentUser currentUser) throws ParseException {

        double totalDebit = 0.00;
        double totalCredit = 0.00;

        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();

        Date currentPeriodFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearFrom());
        Date currentPeriodTo = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearTo());

        if (fromDate != null && toDate != null) {
            currentPeriodFrom = fromDate;
            currentPeriodTo = toDate;
        }
        for (AccProfitAndLossReportDTO accProfitAndLossReportDTO : voucherGroupListDao.getVoucherDetailsByAccTypeAndLedgerType(ledgerId, currentPeriodFrom, currentPeriodTo, fromDate, toDate, currentUser.getCompanyId(), currentUser.getFinancialYearId())) {
           AccProfitAndLossReportDTO profitAndLossReportDTO = new AccProfitAndLossReportDTO();

            if (accProfitAndLossReportDTO.getDrcrAmount() > 0) {
                profitAndLossReportDTO.setCreditAmount(accProfitAndLossReportDTO.getDrcrAmount());
                totalCredit = totalCredit + accProfitAndLossReportDTO.getDrcrAmount();
            }
            if (accProfitAndLossReportDTO.getDrcrAmount() < 0) {
                profitAndLossReportDTO.setDebitAmount(Math.abs(accProfitAndLossReportDTO.getDrcrAmount()));
                totalDebit = totalDebit + Math.abs(accProfitAndLossReportDTO.getDrcrAmount());
            }
            profitAndLossReportDTO.setVoucherTypeName(accProfitAndLossReportDTO.getVoucherTypeName());
            profitAndLossReportDTO.setVoucherNo(accProfitAndLossReportDTO.getVoucherNo());
            profitAndLossReportDTO.setVoucherId(accProfitAndLossReportDTO.getVoucherId());
            profitAndLossReportDTO.setVoucherTypeId(accProfitAndLossReportDTO.getVoucherTypeId());
            profitAndLossReportDTO.setVoucherCreatedDate(accProfitAndLossReportDTO.getVoucherCreatedDate());
            profitAndLossReportDTO.setGroupId(accProfitAndLossReportDTO.getGroupId());
            accProfitAndLossReportDTOList.add(profitAndLossReportDTO);
        }
        if (accProfitAndLossReportDTOList.size() > 0) {
            accProfitAndLossReportDTOList.get(0).setTotalDebit(totalDebit);
            accProfitAndLossReportDTOList.get(0).setTotalCredit(totalCredit);
        }
        return accProfitAndLossReportDTOList;
    }

    public LedgerDTO getOpeningBalance(String ledgerId, Date fromDate, Date toDate, CurrentUser currentUser) throws ParseException {

        FinancialYearDTO preFinancialYearDTO = financialYearSetupService.getPreviousFinancialYearDetail(currentUser.getCompanyId(), currentUser.getFinancialYearId());
        Date currentPeriodFrom = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearFrom());
        Date currentPeriodTo = new SimpleDateFormat("dd-MMM-yyyy").parse(currentUser.getFinancialYearTo());

        if (fromDate != null && toDate != null) {
            currentPeriodFrom = fromDate;
            currentPeriodTo = toDate;
        }

        LedgerDTO ledgerDTO = voucherGroupListDao.getOpeningBalance(ledgerId);
        //This will voucher amount before fromDate
        Double tnxAmtBeforeFromDate = voucherGroupListDao
                .getClosingBalOfPreviousYear(currentUser.getCompanyId(), ledgerId, currentPeriodFrom);
        tnxAmtBeforeFromDate = tnxAmtBeforeFromDate == null ? 0 : tnxAmtBeforeFromDate;

        ledgerDTO.setOpeningBal(ledgerDTO.getOpeningBal() + (tnxAmtBeforeFromDate));

        //To get Net profit from PNL of previous Year
        Double previousYearProfitAndLossAmount = 0.00;
        if (preFinancialYearDTO != null) {
            previousYearProfitAndLossAmount = financialPositionService.getPrePNLAmt(currentUser, preFinancialYearDTO.getFinancialYearFrom(),
                    preFinancialYearDTO.getFinancialYearTo(), preFinancialYearDTO.getFinancialYearId());
        }

        //profit and loss for only capital Account 6= is capital
        Double currentYearProfitAndLossAmount = 0.0;

        if (voucherGroupListDao.checkIsCapital(ledgerId, 6)) {
            currentYearProfitAndLossAmount = financialPositionService.getPNLAmt(currentUser,
                    currentPeriodFrom, currentPeriodTo);
        }

        ledgerDTO.setRetainedEarning(previousYearProfitAndLossAmount);
        ledgerDTO.setCurrentEarning(currentYearProfitAndLossAmount);

        if (ledgerDTO.getAccTypeId().equals(AccountTypeEnum.MATERIAL.getValue())) {
            ledgerDTO.setOpeningBal(voucherGroupListDao.getMaterialOpeningAmt(currentUser.getCompanyId(), currentPeriodFrom, currentPeriodTo));
        }

        return ledgerDTO;
    }


    public List<AccProfitAndLossReportDTO> getInventoryVoucherDetail(Integer accTypeId) {
        List<AccProfitAndLossReportDTO> accProfitAndLossReportDTOList = new ArrayList<>();
        double totalDebit = 0.00;
        double totalCredit = 0.00;

        if (accTypeId == 333) { // 333 is purchase
            accProfitAndLossReportDTOList = voucherGroupListDao.getInventoryPurchaseVoucherDetail();
        } else {
            accProfitAndLossReportDTOList = voucherGroupListDao.getInventorySalesVoucherDetail();
        }

        for (AccProfitAndLossReportDTO accProfitAndLossReportDTO : accProfitAndLossReportDTOList) {
            totalDebit = totalDebit + accProfitAndLossReportDTO.getDebitAmount();
            totalCredit = totalCredit + accProfitAndLossReportDTO.getCreditAmount();
        }

        accProfitAndLossReportDTOList.get(0).setTotalDebit(totalDebit);
        accProfitAndLossReportDTOList.get(0).setTotalCredit(totalCredit);

        return accProfitAndLossReportDTOList;
    }


    public ResponseMessage saveBankReconciliation(BankReconciliationDTO bankReconciliationDTO, CurrentUser currentUser) {

        ResponseMessage responseMessage = new ResponseMessage();
        BankReconciliation bankReconciliation = new BankReconciliation();

        bankReconciliation.setBookBalance(bankReconciliationDTO.getBookBalance());
        bankReconciliation.setChequeIssuedNotEncash(bankReconciliationDTO.getChequeIssuedNotEncash());
        bankReconciliation.setDirectDeposit(bankReconciliationDTO.getDirectDeposit());
        bankReconciliation.setDirectTransfer(bankReconciliationDTO.getDirectTransfer());
        bankReconciliation.setPreviousMonthChequeEncash(bankReconciliationDTO.getPreviousMonthChequeEncash());
        bankReconciliation.setBankReconciliationAmount(bankReconciliationDTO.getBankReconciliationAmount());
        bankReconciliation.setCompanyId(currentUser.getCompanyId());
        bankReconciliation.setSetDate(currentUser.getCreatedDate());
        bankReconciliation.setCreatedBy(currentUser.getLoginId());

        voucherGroupListDao.saveBankReconciliation(bankReconciliation);

        responseMessage.setStatus(1);
        responseMessage.setText("Save successful.");
        return responseMessage;
    }

    //    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage deleteLedgerVoucherDetails(Integer voucherNo, Integer voucherTypeId, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        //delete from voucher details
        voucherCreationDao.getVoucherIdByVoucherNo(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId(), voucherTypeId).forEach(voucherCreationDao::deleteVoucherDetailList);
        //delete from voucher
        Integer voucherId = voucherCreationDao.getVoucherIdFromVoucherTable(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId(), voucherTypeId);
        if (voucherId != null) {
            voucherCreationDao.deleteVoucherItemsFromVoucherTable(voucherId);
        }

        //region delete sale related data
        Integer saleRecordId = voucherCreationDao.getSaleRecordIdByVoucherNo(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId());
        if (saleRecordId != null) {
            voucherCreationDao.deleteFromSaleDetail(saleRecordId);
            voucherCreationDao.deleteFromSale(saleRecordId);
        }
        //endregion
        //region delete asset sale related data
        Integer saleAssetRecordId = voucherCreationDao.getAssetSaleRecordIdByVoucherNo(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId());
        if (saleAssetRecordId != null) {
            voucherCreationDao.deleteFromSaleAssetDetail(saleAssetRecordId);
            voucherCreationDao.deleteFromAssetSale(saleAssetRecordId);
        }
        //endregion

        responseMessage.setStatus(1);
        responseMessage.setText("Deleted successfully.");

        return responseMessage;
    }

    /**
     * delete for sale
     *
     * @param voucherNo
     * @param currentUser
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage updateSaleDetailTable(Integer voucherNo, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        SaleItemListDTO saleItemListDTO = voucherCreationDao.getSaleDetailByVoucherNo(voucherNo, currentUser.getCompanyId(), currentUser.getFinancialYearId());
        if (saleItemListDTO != null) {
            //update the deleted qty to purchase table
            voucherCreationDao.updatePurchaseTable(saleItemListDTO.getQty(), saleItemListDTO.getItemCode(), currentUser.getCompanyId(), currentUser.getFinancialYearId());

            //delete from the sale
            voucherCreationDao.deleteFromSaleDetail(saleItemListDTO.getSaleRecordId());

            //delete from main table
            voucherCreationDao.deleteFromSale(saleItemListDTO.getSaleRecordId());
        }
        responseMessage.setStatus(1);
        responseMessage.setText("Deleted successfully.");

        return responseMessage;
    }


}
