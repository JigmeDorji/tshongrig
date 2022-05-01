package com.spms.mvc.web;

import com.spms.mvc.dto.EmployeeAdvanceDTO;
import com.spms.mvc.dto.MoneyReceiptDTO;
import com.spms.mvc.entity.EmployeeSetup;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.NumberInWords;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.report.domain.dto.ReportResponseDto;
import com.spms.mvc.service.EmployeeAdvanceService;
import com.spms.mvc.service.EmployeeSetupService;
import com.spms.mvc.service.MoneyReceiptService;
import com.spms.mvc.service.SalaryRemittanceService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by jigme.dorji on 4/18/2021.
 */

@Controller
@RequestMapping("/employeeAdvance")
public class EmployeeAdvanceController extends BaseController {

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private EmployeeSetupService employeeSetupService;

    @Autowired
    private EmployeeAdvanceService employeeAdvanceService;

    @Autowired
    private SalaryRemittanceService salaryRemittanceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        model.addAttribute("employeeList", employeeSetupService.getEmployeeList(currentUser.getCompanyId()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        model.addAttribute("currentDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        return new ModelAndView("employeeAdvance");
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, EmployeeAdvanceDTO employeeAdvanceDTO) throws
            IOException, ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return employeeAdvanceService.save(employeeAdvanceDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/generatePaymentVoucher", method = RequestMethod.POST)
    public ResponseMessage generatePaymentVoucher(HttpServletRequest request,Integer id, String voucherNo,Double amount) {
        ResponseMessage responseMessage = new ResponseMessage();
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("financialYearId", currentUser.getFinancialYearId());
        params.put("companyId", currentUser.getCompanyId());
        params.put("printedDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("userName", currentUser.getLoginId());
        params.put("id",id);
        params.put("voucherNo",voucherNo);
        params.put("amountInWords", NumberInWords.convert(amount));


        String reportSourcePath = getReportSourcePath(request);
        String reportOutputPath = getReportOutputPath(request);

        String reportJRXML = "/employeeAdvance.jrxml";
        String reportFormat = "pdf";

        try {
            ReportResponseDto reportResponseDto = salaryRemittanceService.generateReport(reportFormat, params,
                    reportSourcePath, reportOutputPath, currentUser.getLoginId(), reportJRXML, "Salary Advance");
            responseMessage.setDTO(reportResponseDto);
            responseMessage.setStatus(1);
        } catch (Exception ex) {
            responseMessage.setStatus(0);
            responseMessage.setText("Report generation failed. " + ex);
        }
        return responseMessage;
    }
}
