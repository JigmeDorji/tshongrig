package com.spms.mvc.web;


import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.FinancialYearSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Bikash Rai on 5/5/2020.
 */
@Controller
@RequestMapping("/financialYearSetup")
public class FinancialYearSetupController {

    @Autowired
    FinancialYearSetupService financialYearSetupService;

    ResponseMessage responseMessage;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(HttpServletRequest request,Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("financialYearId", financialYearSetupService.getFinancialYearId(currentUser.getCompanyId()));

        return "financialYearSetup";
    }


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request,
                                HttpServletResponse response,
                                FinancialYearDTO financialYearDTO)
            throws Exception {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        responseMessage = financialYearSetupService.save(currentUser, financialYearDTO);
        return responseMessage;
    }


    @ResponseBody
    @RequestMapping(value = "/getFinancialYearList", method = RequestMethod.GET)
    public List<FinancialYearDTO> getFinancialYearList(HttpServletRequest request) throws Exception {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return financialYearSetupService.getFinancialYearList(currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/activateFinancialYear", method = RequestMethod.POST)
    public ResponseMessage activateFinancialYear(HttpServletRequest request,BigInteger financialYearId)
            throws Exception {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return financialYearSetupService.activateFinancialYear(request,currentUser,financialYearId);
    }
}
