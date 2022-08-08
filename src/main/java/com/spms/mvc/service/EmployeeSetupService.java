package com.spms.mvc.service;

import com.spms.mvc.Enumeration.SystemDataInt;
import com.spms.mvc.dao.EmployeeSetupDao;
import com.spms.mvc.dao.SalarySheetDao;
import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.entity.EmployeeSetup;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("employeeSetupService")
public class EmployeeSetupService {

    @Autowired
    private EmployeeSetupDao employeeSetupDao;

    @Autowired
    private SalarySheetDao salarySheetDao;

    public ResponseMessage save(EmployeeSetupDTO employeeSetupDTO, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (employeeSetupDao.checkIsCIDAndBankAccNoAlreadyExist(currentUser.getCompanyId(),employeeSetupDTO.getCidNo(),employeeSetupDTO.getAccNo(),employeeSetupDTO.getEmpId())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("CID or Bank Account number already exists.");
            return responseMessage;
        }

        EmployeeSetup employeeSetup = new EmployeeSetup();
        String companyAbbreviation=currentUser.getCompanyName().replaceAll("\\B.|\\P{L}", "").toUpperCase();


        if (!employeeSetupDTO.getEmpId().equals("")) {
            employeeSetup.setEmpId(employeeSetupDTO.getEmpId());
            employeeSetup.setEmpName(employeeSetupDTO.getEmpName());
            employeeSetup.setDateOfBirth(employeeSetupDTO.getDateOfBirth());
            employeeSetup.setCidNo(employeeSetupDTO.getCidNo());
            employeeSetup.setContactNo(employeeSetupDTO.getContactNo());
            employeeSetup.setTpnNo(employeeSetupDTO.getTpnNo());
            employeeSetup.setDateOfAppointment(employeeSetupDTO.getDateOfAppointment());
            employeeSetup.setBasicSalary(employeeSetupDTO.getBasicSalary());
            employeeSetup.setPost(employeeSetupDTO.getPost());
            employeeSetup.setIncrementAmount(employeeSetupDTO.getIncrementAmount());
            employeeSetup.setIncrementEffectDate(employeeSetupDTO.getIncrementEffectDate());
            employeeSetup.setServiceType(employeeSetupDTO.getServiceType());
            employeeSetup.setAllowance(employeeSetupDTO.getAllowance());
            employeeSetup.setEmailAddress(employeeSetupDTO.getEmailAddress());
            employeeSetup.setVillage(employeeSetupDTO.getVillage());
            employeeSetup.setGewog(employeeSetupDTO.getGewog());
            employeeSetup.setDzongkhag(employeeSetupDTO.getDzongkhag());
            employeeSetup.setCost(employeeSetupDTO.getCost());
            employeeSetup.setAccNo(employeeSetupDTO.getAccNo());
            employeeSetup.setpF(employeeSetupDTO.getpF());
            employeeSetup.setStatus(employeeSetupDTO.getStatus());
            employeeSetup.setgIS(employeeSetupDTO.getgIS());
            employeeSetup.setCompanyId(currentUser.getCompanyId());
            employeeSetup.setCost(employeeSetupDTO.getCost());
            employeeSetup.setCreatedBy(currentUser.getLoginId());

            employeeSetup.setCreatedDate(new Date());

            employeeSetupDao.updateEmployeeSetup(employeeSetup);
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully Updated.");
            return responseMessage;
        } else {
            employeeSetup.setEmpId(companyAbbreviation.concat(employeeSetupDTO.getEmpName().substring(0, 1)).concat(employeeSetupDTO.getCidNo()));
            employeeSetup.setEmpName(employeeSetupDTO.getEmpName());
            employeeSetup.setDateOfBirth(employeeSetupDTO.getDateOfBirth());
            employeeSetup.setCidNo(employeeSetupDTO.getCidNo());
            employeeSetup.setContactNo(employeeSetupDTO.getContactNo());
            employeeSetup.setTpnNo(employeeSetupDTO.getTpnNo());
            employeeSetup.setDateOfAppointment(employeeSetupDTO.getDateOfAppointment());
            employeeSetup.setBasicSalary(employeeSetupDTO.getBasicSalary());
            employeeSetup.setPost(employeeSetupDTO.getPost());
            employeeSetup.setIncrementAmount(employeeSetupDTO.getIncrementAmount());
            employeeSetup.setIncrementEffectDate(employeeSetupDTO.getIncrementEffectDate());
            employeeSetup.setServiceType(employeeSetupDTO.getServiceType());
            employeeSetup.setAllowance(employeeSetupDTO.getAllowance());
            employeeSetup.setEmailAddress(employeeSetupDTO.getEmailAddress());
            employeeSetup.setVillage(employeeSetupDTO.getVillage());
            employeeSetup.setGewog(employeeSetupDTO.getGewog());
            employeeSetup.setDzongkhag(employeeSetupDTO.getDzongkhag());
            employeeSetup.setAccNo(employeeSetupDTO.getAccNo());
            employeeSetup.setpF(employeeSetupDTO.getpF());
            employeeSetup.setgIS(employeeSetupDTO.getgIS());
            employeeSetup.setCost(employeeSetupDTO.getCost());
            employeeSetup.setCompanyId(currentUser.getCompanyId());
            employeeSetup.setStatus(employeeSetupDTO.getStatus());
            employeeSetup.setCreatedBy(currentUser.getLoginId());
            employeeSetup.setCreatedDate(new Date());
            employeeSetup.setCost(employeeSetupDTO.getCost());
            employeeSetupDao.saveEmployeeSetup(employeeSetup);
            responseMessage.setStatus(1);
            responseMessage.setText("Successfully saved.");
            return responseMessage;
        }
    }

    public List<EmployeeSetupDTO> getEmpSetupList(Integer companyId) {
        return employeeSetupDao.getEmpSetupList(companyId);
    }

    public EmployeeSetupDTO getEmpSetUpDetail(String empId) {
        return employeeSetupDao.getEmpSetUpDetail(empId);
    }

    public List<DropdownDTO> getServiceTypeList() {
        return employeeSetupDao.getServiceTypeList();
    }

    public List<DropdownDTO> getEmployeeList(Integer companyId) {
        return employeeSetupDao.getEmployeeList(companyId);
    }

    public SalarySheetDao getSalarySheetDao() {
        return salarySheetDao;
    }

    public void setSalarySheetDao(SalarySheetDao salarySheetDao) {
        this.salarySheetDao = salarySheetDao;
    }
}
