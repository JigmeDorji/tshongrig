package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.LedgerType;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.OtherRemittanceDao;
import com.spms.mvc.dao.StatutoryRemittanceDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.OtherRemittance;
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
 * Created by jigme.dorji on 5/2/2021.
 */
@Service("otherRemittanceService")
public class OtherRemittanceService extends BaseService {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private OtherRemittanceDao otherRemittanceDao;

    @Autowired
    private SalaryRemittanceService salaryRemittanceService;

    @Autowired
    private VoucherCreationService voucherCreationService;


    public ResponseMessage getOtherRemittanceDetails(Integer financialYearId, Integer companyId, Integer month, String bankLedgerId, Integer cost) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<EmployeeSetupDTO> employeeSetupDTOList;

        if (salaryRemittanceService.isSalaryGeneratedInSalarySheet(financialYearId, companyId, month,cost)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Salary sheet not generated for this month.");
            return responseMessage;
        }

        if (otherRemittanceDao.isOtherRemittanceCompleted(financialYearId, companyId, month,cost)) {
            employeeSetupDTOList = otherRemittanceDao.getOtherRemittanceDetails(financialYearId, companyId, month,cost);
            employeeSetupDTOList.get(0).setGenerated(false);
        } else {
            if (bankLedgerId.equals("")) {
                bankLedgerId = null;
            }
            employeeSetupDTOList = otherRemittanceDao.getOtherRemittanceDetails(financialYearId, companyId, month, bankLedgerId,cost);
            if(employeeSetupDTOList.size()>0){
                employeeSetupDTOList.get(0).setGenerated(true);
            }
        }
        responseMessage.setStatus(1);
        responseMessage.setDTO(employeeSetupDTOList);
        return responseMessage;
    }

//    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage remitStatutoryDetail(CurrentUser currentUser, StatutoryRemittanceDTO otherRemittanceDTO) throws ParseException {
        ResponseMessage responseMessage = new ResponseMessage();

        if (!otherRemittanceDao.isOtherRemittanceCompleted(currentUser.getFinancialYearId(), currentUser.getCompanyId(), otherRemittanceDTO.getMonth(), otherRemittanceDTO.getCost())) {
            responseMessage.setStatus(0);
            responseMessage.setText("Other Remittance is completed for this month.");
            return responseMessage;
        }

        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.PAYMENT.getValue());
        voucherDTO.setVoucherEntryDate(new Date()); //system date
        voucherDTO.setVoucherNo(voucherCreationService.getCurrentVoucherNo(VoucherTypeEnum.PAYMENT.getValue(),
                currentUser.getCompanyId(),currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Other Remittance Entry");

        //auto create ledger for party
        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
        //Dr PF_EMPLOYEE
        VoucherDetailDTO voucherDetailDTO = new VoucherDetailDTO();
        voucherDetailDTO.setDrcrAmount(AccountingUtil.drAmount(otherRemittanceDTO.getpF()));
        voucherDetailDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.PF_EMPLOYEE.getText(), currentUser,
                AccountTypeEnum.CURRENT_LIABILITY.getValue()));
        voucherDetailDTOs.add(voucherDetailDTO);

        //Dr PF_EMPLOYER
        VoucherDetailDTO voucherPFEmployer = new VoucherDetailDTO();
        voucherPFEmployer.setDrcrAmount(AccountingUtil.drAmount(otherRemittanceDTO.getpF()));
        voucherPFEmployer.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.PF_EMPLOYER.getText(), currentUser,
                AccountTypeEnum.INDIRECT_COST.getValue()));
        voucherDetailDTOs.add(voucherPFEmployer);

        //Dr GIS
        VoucherDetailDTO voucherDetailHealthContributionDTO = new VoucherDetailDTO();
        voucherDetailHealthContributionDTO.setDrcrAmount(AccountingUtil.drAmount(otherRemittanceDTO.getgIS()));
        voucherDetailHealthContributionDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.GIS.getText(), currentUser,
                AccountTypeEnum.CURRENT_LIABILITY.getValue()));
        voucherDetailDTOs.add(voucherDetailHealthContributionDTO);

        //Cr
        VoucherDetailDTO bankPFEmployer = new VoucherDetailDTO();                           //PF_EMPLOYEE                //PF_EMPLOYER
        bankPFEmployer.setDrcrAmount(AccountingUtil.crAmount(otherRemittanceDTO.getgIS() + otherRemittanceDTO.getpF()+ otherRemittanceDTO.getpF()));
        bankPFEmployer.setLedgerId(otherRemittanceDTO.getBankLedgerId());
        voucherDetailDTOs.add(bankPFEmployer);
        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);

        for (StatutoryRemittanceListDTO otherRemittanceListDTO : otherRemittanceDTO.getStatutoryRemittanceListDTO()) {
            OtherRemittance otherRemittance = new OtherRemittance();
            otherRemittance.setSalarySheetId(otherRemittanceListDTO.getSalarySheetId());
            otherRemittance.setBankLedgerId(otherRemittanceDTO.getBankLedgerId());
            otherRemittance.setFinancialYearId(currentUser.getFinancialYearId());
            otherRemittance.setCompanyId(currentUser.getCompanyId());
            otherRemittance.setCreatedDate(currentUser.getCreatedDate());
            otherRemittance.setCreatedBy(currentUser.getLoginId());
            otherRemittance.setMonth(otherRemittanceDTO.getMonth());
            otherRemittanceDao.save(otherRemittance);
        }

        responseMessage.setStatus(1);
        responseMessage.setText("Other Remittance Remitted Successfully.");
        return responseMessage;
    }
}
