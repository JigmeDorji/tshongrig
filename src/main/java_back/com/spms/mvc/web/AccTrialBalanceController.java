package com.spms.mvc.web;

import com.spms.mvc.dto.AccTrialBalanceDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.service.AccTrialBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 7/30/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accTrialBalance")
public class AccTrialBalanceController {
    @Autowired
    private AccTrialBalanceService accTrialBalanceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request,Model model, ModelAndView modelAndView) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        DateUtil.fromTODateModel(currentUser,model);
        modelAndView.setViewName("accTrialBalance");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/getTrialBalance", method = RequestMethod.GET)
    public List<AccTrialBalanceDTO> getTrialBalance(HttpServletRequest request, Date fromDate, Date toDate) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return accTrialBalanceService.getTrialBalance(currentUser, fromDate, toDate);
    }
}
