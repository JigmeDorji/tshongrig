package com.spms.mvc.web;

import com.spms.mvc.dao.CompanyCreationDao;
import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.CompanyCreation;
import com.spms.mvc.service.CompanyCreationService;
import com.spms.mvc.service.EmployeeSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by SonamPC on 05-Mar-17.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/employeeSetup")
public class EmployeeSetupController {
    @Autowired
    private EmployeeSetupService employeeSetupService;
    @Autowired
    private CompanyCreationService companyCreationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("serviceTypeList", employeeSetupService.getServiceTypeList());
        model.addAttribute("pfPercentage", companyCreationService.getSelectedCompanyDetails(currentUser.getCompanyId()).getPfPercentage());
        return "employeeSetup";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, EmployeeSetupDTO employeeSetupDTO) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return employeeSetupService.save(employeeSetupDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getEmpSetupListList", method = RequestMethod.GET)
    public List<EmployeeSetupDTO> getEmpSetupListList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return employeeSetupService.getEmpSetupList(currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/getEmpSetUpDetail", method = RequestMethod.GET)
    public EmployeeSetupDTO getEmpSetUpDetail(String empId) {
        return employeeSetupService.getEmpSetUpDetail(empId);
    }

}
