package com.spms.mvc.web;

import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.CompanyCreationService;
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

import static com.spms.mvc.library.helper.DateUtil.firstDayOfYear;
import static com.spms.mvc.library.helper.DateUtil.lastDayOfYear;

/**
 * Description: YearClosingController
 * Date:  2020-Nov-14
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2020-Nov-14
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/yearClosing")
public class YearClosingController {
    @Autowired
    CompanyCreationService companyCreationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(Model model, ModelAndView modelAndView) {
        model.addAttribute("fromDate", DateUtil.format(firstDayOfYear(new Date()), DateUtil.DD_MMM_YYYY));
        model.addAttribute("toDate", DateUtil.format(lastDayOfYear(new Date()), DateUtil.DD_MMM_YYYY));
        modelAndView.setViewName("yearClosing");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/performYearClosing", method = RequestMethod.POST)
    public ResponseMessage performYearClosing(HttpServletRequest request) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyCreationService.performYearClosing(currentUser.getCompanyId(),
                currentUser.getFinancialYearId());
    }

}
