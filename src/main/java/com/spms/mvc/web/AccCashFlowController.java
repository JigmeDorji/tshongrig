package com.spms.mvc.web;

import com.spms.mvc.dto.AccCashFlowDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.service.AccCashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 8/4/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accCashFlow")
public class AccCashFlowController {

    @Autowired
    private AccCashFlowService accCashFlowService;




    @ResponseBody
    @RequestMapping(value = "/getCashFlow", method = RequestMethod.GET)
    public List<AccCashFlowDTO> getCashFlow(HttpServletRequest request, Date fromDate, Date toDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return accCashFlowService.getCashFlow(currentUser, fromDate, toDate);
    }

}
