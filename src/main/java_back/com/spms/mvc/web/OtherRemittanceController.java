package com.spms.mvc.web;

import com.spms.mvc.dto.StatutoryRemittanceDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.report.domain.dto.ReportResponseDto;
import com.spms.mvc.service.MoneyReceiptService;
import com.spms.mvc.service.OtherRemittanceService;
import com.spms.mvc.service.SalaryRemittanceService;
import com.spms.mvc.service.StatutoryRemittanceService;
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
 * Created by jigme.dorji on 5/2/2021.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/otherRemittance")
public class OtherRemittanceController extends  BaseController{

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private SalaryRemittanceService salaryRemittanceService;

    @Autowired
    private OtherRemittanceService otherRemittanceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {

        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute("currentUser");

        model.addAttribute("monthList", salaryRemittanceService.getMonthList());
        model.addAttribute("bankList", moneyReceiptService
                .getBankList(currentUser.getCompanyId()));
        return "otherRemittance";
    }

    @ResponseBody
    @RequestMapping(value = "/getOtherRemittanceDetails", method = RequestMethod.GET)
    public ResponseMessage getOtherRemittanceDetails(Integer month, Integer cost, String bankLedgerId, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute("currentUser");
        return otherRemittanceService.getOtherRemittanceDetails(currentUser.getFinancialYearId(), currentUser.getCompanyId(), month, bankLedgerId,cost);
    }

    @ResponseBody
    @RequestMapping(value = "/remitOtherRemittance", method = RequestMethod.POST)
    public ResponseMessage remitStatutoryDetail(StatutoryRemittanceDTO statutoryRemittanceDTO, HttpServletRequest request) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute("currentUser");
        return otherRemittanceService.remitStatutoryDetail(currentUser, statutoryRemittanceDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/generateReport", method = {RequestMethod.GET})
    public ResponseMessage generateReport(HttpServletRequest request, String reportFormat, Integer month,String monthText,Integer cost) {
        ResponseMessage responseMessage = new ResponseMessage();
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("month", month);
        params.put("monthText", monthText);
        params.put("cost", cost);
        params.put("financialYearId", currentUser.getFinancialYearId());
        params.put("companyId", currentUser.getCompanyId());
        params.put("printedDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("userName", currentUser.getLoginId());

        String reportSourcePath = getReportSourcePath(request);
        String reportOutputPath = getReportOutputPath(request);

        String reportJRXML = "/otherRemittance.jrxml";

        try {
            ReportResponseDto reportResponseDto = otherRemittanceService.generateReport(reportFormat, params,
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
