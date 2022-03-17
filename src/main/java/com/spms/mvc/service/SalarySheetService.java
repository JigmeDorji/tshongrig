package com.spms.mvc.service;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.CostEnum;
import com.spms.mvc.Enumeration.LedgerType;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.EmployeeSetupDao;
import com.spms.mvc.dao.LedgerDao;
import com.spms.mvc.dao.SalarySheetDao;
import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.dto.SalarySheetDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.SalarySheet;
import com.spms.mvc.global.service.BaseService;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 4/7/2021.
 */
@Service("salarySheetService")
public class SalarySheetService extends BaseService {
    ResponseMessage responseMessage = new ResponseMessage();
    @Autowired
    private SalarySheetDao salarySheetDao;

    @Autowired
    private EmployeeSetupService employeeSetupService;

    @Autowired
    private VoucherCreationService voucherCreationService;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private EmployeeSetupDao employeeSetupDao;

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private CompanyCreationService companyCreationService;


    //    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage save(SalarySheetDTO salarySheetDTO, CurrentUser currentUser) throws ParseException {

        //Salary sheet accounting entry
        VoucherDTO voucherDTO = new VoucherDTO();
        voucherDTO.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
        voucherDTO.setVoucherEntryDate(new Date());
        voucherDTO.setVoucherNo(voucherCreationService
                .getCurrentVoucherNo(VoucherTypeEnum.JOURNAL.getValue(),
                        currentUser.getCompanyId(), currentUser.getFinancialYearId()));
        voucherDTO.setNarration("Salary Sheet");

        //region cr voucher preparation
        List<VoucherDetailDTO> voucherDetailDTOs = new ArrayList<>();
        VoucherDetailDTO salaryVoucherDto = new VoucherDetailDTO();
        salaryVoucherDto.setDrcrAmount(AccountingUtil.drAmount(salarySheetDTO.getTotalGrossSalary()));
//        voucherSaleDTO.setIsCash(saleItemDTO.getIsCash());
        Integer accountTypeId = salarySheetDTO.getCost().equals(CostEnum.GENERAL.getValue()) ?
                AccountTypeEnum.INDIRECT_COST.getValue() :
                AccountTypeEnum.DIRECT_COST.getValue();
        if (salarySheetDTO.getCost().equals(CostEnum.GENERAL.getValue())) {
            salaryVoucherDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.SALARY_admin.getText(),
                    currentUser, accountTypeId));
        } else {
            salaryVoucherDto.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.SALARY_Production.getText(),
                    currentUser, accountTypeId));
        }
        voucherDetailDTOs.add(salaryVoucherDto);
        //endregion

        //region Dr voucher preparation
        //(PF)
        if (salarySheetDTO.getTotalPF() > 0) {
            VoucherDetailDTO pFEmployeeVoucherDTO = new VoucherDetailDTO();
            pFEmployeeVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salarySheetDTO.getTotalPF()));
            pFEmployeeVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.PF_EMPLOYEE.getText(),
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));
            voucherDetailDTOs.add(pFEmployeeVoucherDTO);
        }

        //(GIS)
        if (salarySheetDTO.getTotalGIS() > 0) {
            VoucherDetailDTO gISVoucherDTO = new VoucherDetailDTO();
            gISVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salarySheetDTO.getTotalGIS()));
            gISVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.GIS.getText(),
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));
            voucherDetailDTOs.add(gISVoucherDTO);
        }


        //(Advance)
//        VoucherDetailDTO advanceVoucherDTO = new VoucherDetailDTO();
//        advanceVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salarySheetDTO.getTotalAdvance()));
//        advanceVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.ADVANCE.getText(),
//                currentUser, AccountTypeEnum.EMPLOYEE_ADVANCE.getValue()));
//        voucherDetailDTOs.add(advanceVoucherDTO);

        //(HC)
        if (salarySheetDTO.getTotalHC() > 0) {
            VoucherDetailDTO hCVoucherDTO = new VoucherDetailDTO();
            hCVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salarySheetDTO.getTotalHC()));
            hCVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.HC.getText(),
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));
            voucherDetailDTOs.add(hCVoucherDTO);
        }

        //(Salary TDS)
        if (salarySheetDTO.getTotalTDS() > 0) {
            VoucherDetailDTO tDSVoucherDTO = new VoucherDetailDTO();
            tDSVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salarySheetDTO.getTotalTDS()));
            tDSVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.SALARY_TDS.getText(),
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));
            voucherDetailDTOs.add(tDSVoucherDTO);
        }


        //(Salary Payable)
        if (salarySheetDTO.getTotalTakeHome() > 0) {
            VoucherDetailDTO salaryPayableVoucherDTO = new VoucherDetailDTO();
            salaryPayableVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(salarySheetDTO.getTotalTakeHome()));
            salaryPayableVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(LedgerType.SALARY_PAYABLE.getText(),
                    currentUser, AccountTypeEnum.PAYABLE.getValue()));
            voucherDetailDTOs.add(salaryPayableVoucherDTO);
        }



//        responseMessage = voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
//        if ((responseMessage.getStatus().equals(0))) {
//            return responseMessage;
//        }
        //endregion


        //inter the salary sheet details
        Integer salarySheetId = salarySheetDao.getMaxSalarySheetId();
        for (EmployeeSetupDTO employeeSetupDTO : salarySheetDTO.getEmployeeSetupDTOS()) {
            if (employeeSetupDTO.getAdvance() > 0) {
                //region Employee wise advance accounting entry.
//                VoucherDTO voucherDTOAdvance = new VoucherDTO();
//                voucherDTOAdvance.setVoucherTypeId(VoucherTypeEnum.JOURNAL.getValue());
//                voucherDTOAdvance.setVoucherEntryDate(new Date());
//                voucherDTOAdvance.setVoucherNo(voucherDTO.getVoucherNo());
//                voucherDTO.setNarration("Advance Deduction");
//
//                List<VoucherDetailDTO> voucherDetailDTOsAdvance = new ArrayList<>();

                //region Cr particular employee
                VoucherDetailDTO employeeAdvanceVoucherDTO = new VoucherDetailDTO();
                String empName = employeeSetupDTO.getEmpName() + "(" + employeeSetupDTO.getEmpId() + ")";

                employeeAdvanceVoucherDTO.setLedgerId(ledgerService.getLedgerIdByLedgerName(empName,
                        currentUser, AccountTypeEnum.EMPLOYEE_ADVANCE.getValue()));
                employeeAdvanceVoucherDTO.setDrcrAmount(AccountingUtil.crAmount(employeeSetupDTO.getAdvance()));
                voucherDetailDTOs.add(employeeAdvanceVoucherDTO);
//                voucherDetailDTOsAdvance.add(employeeAdvanceVoucherDTO);
                //endregion
            }
            EmployeeSetupDTO employeeSetupDTODetailsDTO = employeeSetupService.getEmpSetUpDetail(employeeSetupDTO.getEmpId());
            employeeSetupDTODetailsDTO.setDeduction(employeeSetupDTO.getDeduction());
            employeeSetupDTODetailsDTO.setAdvance(employeeSetupDTO.getAdvance());
            employeeSetupDTODetailsDTO.setTakeHome(employeeSetupDTO.getTakeHome());
            SalarySheet salarySheet = new ModelMapper().map(employeeSetupDTODetailsDTO, SalarySheet.class);
            salarySheet.setSalarySheetId(salarySheetId + 1);
            salarySheet.setFinancialYearId(currentUser.getFinancialYearId());
            salarySheet.setCreatedBy(currentUser.getLoginId());
            salarySheet.setCreatedDate(new Date());
            salarySheet.setMonthId(salarySheetDTO.getMonthId());
            salarySheet.setNetSalary(employeeSetupDTO.getNetSalary());
            salarySheet.settDS(employeeSetupDTO.gettDS());
            salarySheet.sethC(employeeSetupDTO.gethC());
            salarySheet.setGrossSalary(employeeSetupDTO.getGrossSalary());
            salarySheet.setTotalRecovery(employeeSetupDTO.getTotalRecovery());
            salarySheet.setTakeHome(employeeSetupDTO.getTakeHome());
            salarySheetDao.save(salarySheet);
            salarySheetId = salarySheetId + 1;
        }
        voucherDTO.setVoucherDetailDTOList(voucherDetailDTOs);
        responseMessage = voucherCreationService.performPurchaseAndSaleVoucherEntry(voucherDTO, currentUser);
        if ((responseMessage.getStatus().equals(0))) {
            return responseMessage;
        }
        responseMessage.setStatus(1);
        responseMessage.setText("Salary sheet entry saved successfully.");
        return responseMessage;

    }


    public Integer getMaxMonthId(CurrentUser currentUser, Integer cost) {
        return salarySheetDao.getMaxMonthId(currentUser.getCompanyId(), currentUser.getFinancialYearId(), cost);
    }

    public Double getTDSAmount(Double netSalary) {
        return salarySheetDao.getTDSAmount(netSalary);
    }

    public List<EmployeeSetupDTO> getEmpSetupList(CurrentUser currentUser, Integer selectedMonthId, Integer cost) {
        List<EmployeeSetupDTO> employeeSetupDTOSAfterTDS = new ArrayList<>();
        List<EmployeeSetupDTO> salarySheetData;

        String currentYearStr = currentUser.getFinancialYearFrom();
        String[] dateParts = currentYearStr.split("-");
        String day = dateParts[0];
        String month = dateParts[1];
        Integer currentYear = Integer.parseInt(dateParts[2]);


        List<EmployeeSetupDTO> salarySheetGeneratedData = salarySheetDao.getGeneratedSalarySheet(currentUser.getCompanyId(), selectedMonthId, cost);
        List<EmployeeSetupDTO> employeeSetupData = salarySheetDao.getEmpSetupList(currentUser.getCompanyId(), cost, selectedMonthId, currentYear);
        if (salarySheetGeneratedData.size() > 0) {
            salarySheetData = salarySheetGeneratedData;
        } else {
            Integer maxMonthId = salarySheetDao.getMaxMonthId(currentUser.getCompanyId(), currentUser.getFinancialYearId(), cost);
            if (selectedMonthId < maxMonthId) {
                salarySheetData = null;
            } else {
                salarySheetData = employeeSetupData;
            }
        }
        if (salarySheetData != null) {
            for (EmployeeSetupDTO employeeSetupDTO : salarySheetData) {
                //Add increment amt
                Date incrementAffectDate = employeeSetupDTO.getIncrementEffectDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(incrementAffectDate);
                Integer incrementMonth = calendar.get(Calendar.MONTH) + 1;
                Integer incrementYear = calendar.get(Calendar.YEAR);

                if (selectedMonthId.equals(incrementMonth) && incrementAffectDate.after(employeeSetupDTO.getDateOfAppointment())) {
                    if (salarySheetDao.checkIsIncrementAffectedForCurrentCurrentPeriod(currentUser.getCompanyId(), selectedMonthId, currentYear, employeeSetupDTO.getEmpId())) {
                        Double newBasicSalary = employeeSetupDTO.getBasicSalary() + employeeSetupDTO.getIncrementAmount();
                        BigDecimal pfPercentage = companyCreationService.getSelectedCompanyDetails(currentUser.getCompanyId()).getPfPercentage();
                        BigDecimal newPF = pfPercentage.divide(new BigDecimal(100)).multiply(new BigDecimal(newBasicSalary));
                        salarySheetDao.updateBasicSalary(employeeSetupDTO.getEmpId(), newBasicSalary, newPF, currentUser.getCompanyId());
                        salarySheetDao.insertIncrementAffectedDetail(currentUser.getCompanyId(), selectedMonthId, currentYear, employeeSetupDTO.getIncrementAmount(), employeeSetupDTO.getEmpId());
                    }

                }
            }
        }


        salarySheetGeneratedData = salarySheetDao.getGeneratedSalarySheet(currentUser.getCompanyId(), selectedMonthId, cost);
        employeeSetupData = salarySheetDao.getEmpSetupList(currentUser.getCompanyId(), cost, selectedMonthId, currentYear);
        if (salarySheetGeneratedData.size() > 0) {
            salarySheetData = salarySheetGeneratedData;
        } else {
            Integer maxMonthId = salarySheetDao.getMaxMonthId(currentUser.getCompanyId(), currentUser.getFinancialYearId(), cost);
            if (selectedMonthId < maxMonthId) {
                salarySheetData = null;
            } else {
                salarySheetData = employeeSetupData;
            }
        }
        if (salarySheetData != null) {
            for (EmployeeSetupDTO employeeSetupDTO : salarySheetData) {
                String ledgerName = employeeSetupDTO.getEmpName() + "(" + employeeSetupDTO.getEmpId() + ")";
                String ledgerId = ledgerDao.getLedgerIdByLedgerName(currentUser.getCompanyId(), ledgerName);
                if (ledgerId != null) {
                    employeeSetupDTO.setBalanceAdvance(salarySheetDao.getBalanceAdvance(ledgerId, currentUser.getCompanyId()));
                } else {
                    employeeSetupDTO.setBalanceAdvance(0);
                }
                Double tdsAmt =salarySheetDao.getTDSAmount(employeeSetupDTO.getNetSalary());
                tdsAmt=tdsAmt==null?0.25*(employeeSetupDTO.getNetSalary()-83333)+11875:tdsAmt;
                employeeSetupDTO.settDS(tdsAmt);
                if (salarySheetGeneratedData.size() > 0) {
                    employeeSetupDTO.setTakeHome(employeeSetupDTO.getTakeHome());
                } else {
                    employeeSetupDTO.setTakeHome(employeeSetupDTO.getNetSalary() - (employeeSetupDTO.gettDS() + employeeSetupDTO.gethC()));
                }
                employeeSetupDTOSAfterTDS.add(employeeSetupDTO);
            }
        }

        return employeeSetupDTOSAfterTDS;
    }
}
