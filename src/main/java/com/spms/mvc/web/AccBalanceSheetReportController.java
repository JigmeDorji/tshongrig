package com.spms.mvc.web;

import com.spms.mvc.dto.AccBalanceSheetDTO;
import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.service.AccBalanceSheetReportService;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jigmePc on 5/20/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accBalanceSheetReport")
public class AccBalanceSheetReportController {

    @Autowired
    AccBalanceSheetReportService accBalanceSheetReportService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, Model model, ModelAndView modelAndView) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        DateUtil.fromTODateModel(currentUser,model);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/getAccBalanceSheetReport", method = RequestMethod.GET)
    public List<AccBalanceSheetDTO> getAccBalanceSheetReport(HttpServletRequest request, Date asOnDate) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return accBalanceSheetReportService.getAccBalanceSheetReport(currentUser, asOnDate);
    }

}
