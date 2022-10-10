package com.spms.mvc.web;

import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.service.AccProfitAndLossReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.spms.mvc.library.helper.DateUtil.fromTODateModel;

/**
 * Created by jigmePc on 5/1 2/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accProfitAndLossReport")
public class AccProfitAndLossReportController {

    @Autowired
    private AccProfitAndLossReportService accProfitAndLossReportService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        DateUtil.fromTODateModel(currentUser,model);
        return "accProfitAndLossReport";
    }



    @ResponseBody
    @RequestMapping(value = "/getProfitAndLossDetails", method = RequestMethod.GET)
    public List<AccProfitAndLossReportDTO> getProfitAndLossDetails(HttpServletRequest request, Date fromDate, Date toDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return accProfitAndLossReportService.getProfitAndLossDetails(currentUser.getCompanyId(), fromDate,
                toDate,currentUser.getBusinessType(),currentUser.getFinancialYearId());
    }

}
