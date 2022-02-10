package com.spms.mvc.web;

import com.spms.mvc.dto.SalaryRemittanceDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.report.domain.dto.ReportResponseDto;
import com.spms.mvc.service.MoneyReceiptService;
import com.spms.mvc.service.SalaryRemittanceService;
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
import java.util.Map;

/**
 * Description: SalaryRemittanceController
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

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/salaryRemittance")
public class SalaryRemittanceController extends BaseController {

    @Autowired
    private SalaryRemittanceService salaryRemittanceService;

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute("currentUser");
        model.addAttribute("monthList", salaryRemittanceService.getMonthList());
        model.addAttribute("bankList", moneyReceiptService
                .getBankList(currentUser.getCompanyId()));
        return "salaryRemittance";
    }

    @ResponseBody
    @RequestMapping(value = "/getEmployeeSalarySheet", method = RequestMethod.GET)
    public ResponseMessage getEmployeeSalarySheet(HttpServletRequest request, Integer month, String bankLedgerId, Integer cost) {
        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute("currentUser");
        return salaryRemittanceService.getEmployeeSalarySheet(currentUser.getFinancialYearId(), currentUser.getCompanyId(), month, bankLedgerId, cost);
    }

    @ResponseBody
    @RequestMapping(value = "/remitSalary", method = RequestMethod.POST)
    public ResponseMessage remitSalary(HttpServletRequest request, SalaryRemittanceDTO salaryRemittanceDTO) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return salaryRemittanceService.remitSalary(currentUser, salaryRemittanceDTO, salaryRemittanceDTO.getCost());
    }

    @ResponseBody
    @RequestMapping(value = "/generateReport", method = {RequestMethod.GET})
    public ResponseMessage generateReport(HttpServletRequest request, String reportFormat, Integer month,Integer cost) {
        ResponseMessage responseMessage = new ResponseMessage();
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("month", month);
        params.put("cost", cost);
        params.put("financialYearId", currentUser.getFinancialYearId());
        params.put("companyId", currentUser.getCompanyId());
        params.put("printedDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("userName", currentUser.getLoginId());

        String reportSourcePath = getReportSourcePath(request);
        String reportOutputPath = getReportOutputPath(request);

        String reportJRXML = "/salaryRemittanceReport.jrxml";

        try {
            ReportResponseDto reportResponseDto = salaryRemittanceService.generateReport(reportFormat, params,
                    reportSourcePath, reportOutputPath, currentUser.getLoginId(), reportJRXML, "Salary Remittance");
            responseMessage.setDTO(reportResponseDto);
            responseMessage.setStatus(1);
        } catch (Exception ex) {
            responseMessage.setStatus(0);
            responseMessage.setText("Report generation failed. " + ex);
        }
        return responseMessage;
    }

}
