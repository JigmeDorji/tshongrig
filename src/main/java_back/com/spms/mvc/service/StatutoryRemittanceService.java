package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.LedgerType;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.StatutoryRemittanceDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.StatutoryRemittance;
import com.spms.mvc.global.service.BaseService;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: StatutoryRemittanceService
 * Date:  2021-Apr-11
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Apr-11
 * Change Description:
 * Search Tag:
 */
@Service("statutoryRemittanceService")
public class StatutoryRemittanceService extends BaseService {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private StatutoryRemittanceDao statutoryRemittanceDao;

    @Autowired
    private SalaryRemittanceService salaryRemittanceService;

    @Autowired
    private VoucherCreationService voucherCreationService;


    public ResponseMessage getStatutoryDetails(Integer financialYearId, Integer companyId, Integer month, String bankLedgerId, Integer cost) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<EmployeeSetupDTO> employeeSetupDTOList;

        if (salaryRemittanceService.isSalaryGeneratedInSalarySheet(financialYearId, companyId, month, cost)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Salary sheet not generated for this month.");
            return responseMessage;
        }

        if (statutoryRemittanceDao.isStatutoryRemittanceCompleted(financialYearId, companyId, month, cost)) {
            employeeSetupDTOList = statutoryRemittanceDao.getStatutoryDetails(financialYearId, companyId, month, cost);
            employeeSetupDTOList.get(0).setGenerated(false);
        } else {
            if (bankLedgerId.equals("")) {
                bankLedgerId = null;
            }
            employeeSetupDTOList = statutoryRemittanceDao.getStatutoryDetails(financialYearId, companyId, month, bankLedgerId, cost);
            employeeSetupDTOList.get(0).setGenerated(true);
        }
        responseMessage.setStatus(1);
        responseMessage.setDTO(employeeSetupDTOList);
        return responseMessage;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage remitStatutoryDetail(CurrentUser currentUser, StatutoryRemittanceDTO statutoryRemittanceDTO) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();

        if (!statutoryRemittanceDao.isStatutoryRemittanceCompleted(currentUser.getFinancialYearId(), currentUser.getCompanyId(), statutoryRemittanceDTO.getMonth(), statutoryRemittanceDTO.getCost())) {
            responseMessage.setStatus(0);
            responseMessage.setText("Statutory Remittance is completed for this month.");
            return responseMessage;
        }

        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
        voucherDTO.setVoucherEntryDate(new Date()); //system date
        voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(),
                currentUser.getCompanyId(), currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Statutory Remittance Entry");

        //auto create ledger for party
        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
        //Dr
        if (statutoryRemittanceDTO.gettDS() > 0) {
            VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
            voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(statutoryRemittanceDTO.gettDS()));
            voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.SALARY_TDS.getText(), currentUser,
                    AccountTypeEnum.CURRENT_LIABILITY.getValue()));
            voucherDetailDTOs.add(voucherDetailDTO);
        }


        //Dr
        VoucherDetailDTO voucherDetailHealthContributionDTO = new VoucherDetailDTO();
        voucherDetailHealthContributionDTO.setDrcrAmount(AccountingUtil.drAmount(statutoryRemittanceDTO.gethC()));
        voucherDetailHealthContributionDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.HC.getText(), currentUser,
                AccountTypeEnum.CURRENT_LIABILITY.getValue()));
        voucherDetailDTOs.add(voucherDetailHealthContributionDTO);

        //Cr
        VoucherDetailDTO bankVoucherDTO = new VoucherDetailDTO();
        bankVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(statutoryRemittanceDTO.gettDS() + statutoryRemittanceDTO.gethC()));
        bankVoucherDTO.setLedgerId(statutoryRemittanceDTO.getBankLedgerId());
        voucherDetailDTOs.add(bankVoucherDTO);
        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        for (StatutoryRemittanceListDTO statutoryRemittanceListDTO : statutoryRemittanceDTO.getStatutoryRemittanceListDTO()) {
            StatutoryRemittance statutoryRemittance = new StatutoryRemittance();
            statutoryRemittance.setSalarySheetId(statutoryRemittanceListDTO.getSalarySheetId());
            statutoryRemittance.setBankLedgerId(statutoryRemittanceDTO.getBankLedgerId());
            statutoryRemittance.setFinancialYearId(currentUser.getFinancialYearId());
            statutoryRemittance.setCompanyId(currentUser.getCompanyId());
            statutoryRemittance.setCreatedDate(currentUser.getCreatedDate());
            statutoryRemittance.setCreatedBy(currentUser.getLoginId());
            statutoryRemittance.setMonth(statutoryRemittanceDTO.getMonth());
            statutoryRemittanceDao.save(statutoryRemittance);
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Statutory Remitted Successfully.");

        return responseMessage;
    }

}
