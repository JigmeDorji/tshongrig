package com.spms.mvc.web;

import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.dto.SalarySheetDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.report.domain.dto.ReportResponseDto;
import com.spms.mvc.service.EmployeeSetupService;
import com.spms.mvc.service.SalaryRemittanceService;
import com.spms.mvc.service.SalarySheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jigme.dorji on 4/7/2021.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/salarySheet")
public class SalarySheetController extends BaseController {

    @Autowired
    private EmployeeSetupService employeeSetupService;

    @Autowired
    private SalarySheetService salarySheetService;

    @Autowired
    private SalaryRemittanceService salaryRemittanceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("monthList", salarySheetService.getMonthList());
        return "salarySheet";
    }

    @ResponseBody
    @RequestMapping(value = "/getEmpListDetails", method = RequestMethod.GET)
    public List<EmployeeSetupDTO> getEmpListDetails(HttpServletRequest request, Integer selectedMonthId, Integer cost) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
//        List<EmployeeSetupDTO> sd = salarySheetService.getEmpSetupList(currentUser, selectedMonthId, cost);
        return salarySheetService.getEmpSetupList(currentUser, selectedMonthId, cost);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, SalarySheetDTO salarySheetDTO) throws
            ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return salarySheetService.save(salarySheetDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getMaxMonthId", method = RequestMethod.GET)
    public Integer getMaxMonthId(HttpServletRequest request, Integer cost) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return salarySheetService.getMaxMonthId(currentUser, cost);
    }

    @ResponseBody
    @RequestMapping(value = "/getTDSAmount", method = RequestMethod.GET)
    public Double getTDSAmount(Double netSalary) {
        return salarySheetService.getTDSAmount(netSalary);
    }

    @ResponseBody
    @RequestMapping(value = "/generateReport", method = {RequestMethod.GET})
    public ResponseMessage generateReport(HttpServletRequest request, String reportFormat, Integer month, String monthText, Integer cost) {
        ResponseMessage responseMessage = new ResponseMessage();
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("month", month);
        params.put("cost", cost);
        params.put("monthText", monthText);
        params.put("financialYearId", currentUser.getFinancialYearId());
        params.put("companyId", currentUser.getCompanyId());
        params.put("printedDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("userName", currentUser.getLoginId());

        String reportSourcePath = getReportSourcePath(request);
        String reportOutputPath = getReportOutputPath(request);

        String reportJRXML = "/salarySheet.jrxml";

        try {
            ReportResponseDto reportResponseDto = salaryRemittanceService.generateReport(reportFormat, params,
                    reportSourcePath, reportOutputPath, currentUser.getLoginId(), reportJRXML, "Salary Sheet");
            responseMessage.setDTO(reportResponseDto);
            responseMessage.setStatus(1);
        } catch (Exception ex) {
            responseMessage.setStatus(0);
            responseMessage.setText("Report generation failed. " + ex);
        }
        return responseMessage;
    }
}
