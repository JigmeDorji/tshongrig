package com.spms.mvc.service;

import com.spms.mvc.Enumeration.LedgerType;
import com.spms.mvc.dao.SalaryRemittanceDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.SalaryRemittance;
import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
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
 * Description: SalaryRemittanceService
 * Date:  2021-Apr-07
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Apr-07
 * Change Description:
 * Search Tag:
 */
@Service("salaryRemittanceService")
public class SalaryRemittanceService extends BaseService {

    @Autowired
    private SalaryRemittanceDao salaryRemittanceDao;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;


    public ResponseMessage getEmployeeSalarySheet(Integer financialYearId, Integer companyId, Integer month, String bankLedgerId, Integer cost) {

        List<SalaryRemittanceDTO> employeeSetupDTOList;
        ResponseMessage responseMessage = new ResponseMessage();

        if (isSalaryGeneratedInSalarySheet(financialYearId, companyId, month, cost)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Salary sheet not generated for this month.");
            return responseMessage;
        }

        if (salaryRemittanceDao.isSalaryRemittanceCompletedForMonth(financialYearId, companyId, month,cost)) {
            employeeSetupDTOList = salaryRemittanceDao.getEmployeeSalarySheet(financialYearId,
                    companyId, month,cost);
            employeeSetupDTOList.get(0).setGenerated(false);
        } else {
            if (bankLedgerId.equals("")) {
                bankLedgerId = null;
            }
            employeeSetupDTOList = salaryRemittanceDao.getEmployeeDetailFromSalaryRemittance(financialYearId,
                    companyId, month, bankLedgerId,cost);
            if(employeeSetupDTOList.size()>0){
                employeeSetupDTOList.get(0).setGenerated(true);
            }

        }
        responseMessage.setStatus(1);
        responseMessage.setDTO(employeeSetupDTOList);
        return responseMessage;
    }

    public Boolean isSalaryGeneratedInSalarySheet(Integer financialYearId, Integer companyId, Integer month, Integer cost) {
        return salaryRemittanceDao.isSalaryGeneratedInSalarySheet(financialYearId, companyId, month,cost);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage remitSalary(CurrentUser currentUser, SalaryRemittanceDTO salaryRemittanceDTO,Integer cost) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();


        if (!salaryRemittanceDao.isSalaryRemittanceCompletedForMonth(currentUser.getFinancialYearId(), currentUser.getCompanyId(), salaryRemittanceDTO.getMonth(), cost)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Salary Remittance is completed for this month.");
            return responseMessage;
        }

        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
        voucherDTO.setVoucherEntryDate(new Date()); //system date
        voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(),
                currentUser.getCompanyId(),currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Salary Remittance Entry");

        //auto create ledger for party
        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
        //Cr
        VoucherDetailDTO salaryPayableDTO = new VoucherDetailDTO();
        salaryPayableDTO.setDrcrAmount(AccountingUtil.drAmount(salaryRemittanceDTO.getTotalAmount()));
        salaryPayableDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.SALARY_PAYABLE.getText(), currentUser,
                AccountTypeEnum.RECEIVABLE.getValue()));
        voucherDetailDTOs.add(salaryPayableDTO);

        //Dr
        VoucherDetailDTO bankVoucherDTO = new VoucherDetailDTO();
        bankVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salaryRemittanceDTO.getTotalAmount()));
        bankVoucherDTO.setLedgerId(salaryRemittanceDTO.getBankLedgerId());
        voucherDetailDTOs.add(bankVoucherDTO);

        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        //save to remittance table
        for (SalaryRemittanceListDTO salaryRemittanceListDTO : salaryRemittanceDTO.getSalaryRemittanceListDTOList()) {
            SalaryRemittance salaryRemittance = new SalaryRemittance();
            salaryRemittance.setSalarySheetId(salaryRemittanceListDTO.getSalarySheetId());
            salaryRemittance.setBankLedgerId(salaryRemittanceDTO.getBankLedgerId());
            salaryRemittance.setFinancialYearId(currentUser.getFinancialYearId());
            salaryRemittance.setMonth(salaryRemittanceDTO.getMonth());
            salaryRemittance.setCompanyId(currentUser.getCompanyId());
            salaryRemittance.setCreatedBy(currentUser.getLoginId());
            salaryRemittance.setCreatedDate(currentUser.getCreatedDate());
            salaryRemittanceDao.save(salaryRemittance);
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Salary Remitted Successfully.");

        return responseMessage;
    }
}
