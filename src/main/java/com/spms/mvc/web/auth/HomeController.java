
package com.spms.mvc.web.auth;

import com.spms.mvc.dao.CompanyCreationDao;
import com.spms.mvc.dto.CompanyCreationDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.service.CompanyCreationService;
import com.spms.mvc.service.FinancialYearSetupService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "")
@PreAuthorize("isAuthenticated()")
public class HomeController {

    @Autowired
    private CompanyCreationService companyCreationService;

    @Autowired
    private FinancialYearSetupService financialYearSetupService;

    @Autowired
    private CompanyCreationDao companyCreationDao;

    /**
     * home controller
     *
     * @param request        request
     * @param response       response
     * @param authentication authentication
     * @return ModelAndView
     */

    @RequestMapping(value = {"/", "home"})
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        CurrentUser currentUser = new CurrentUser();

        currentUser.setLoginId(userDTO.getUsername());
        currentUser.setCreatedDate(userDTO.getCreatedDate());
        currentUser.setTxtUserName(userDTO.getUserFullName());
        currentUser.setUserStatus(userDTO.getUserStatus());
        currentUser.setUserRoleTypeId(userDTO.getUserRoleTypeId());

        CompanyCreationDTO companyCreationDTO = companyCreationService.getSelectedCompanyDetails(userDTO.getCompanyId());
        FinancialYearDTO financialYearDTO = companyCreationService.getCurrentFinancialYearIdByCompany(userDTO.getCompanyId());

        currentUser.setCompanyName(companyCreationDTO.getCompanyName());
        currentUser.setCompanyId(companyCreationDTO.getCompanyId());

        //region Auto financial year creation
        if (new Date().after(financialYearDTO.getFinancialYearTo())) {
            Date newFromDate = DateUtils.addYears(financialYearDTO.getFinancialYearFrom(), 1);
            Date newToDate = DateUtils.addYears(financialYearDTO.getFinancialYearTo(), 1);
            financialYearDTO.setFinancialYearFrom(newFromDate);
            financialYearDTO.setFinancialYearTo(newToDate);
            financialYearSetupService.makeInactiveFinancialYear(currentUser);
            if (!financialYearSetupService.checkIsFinancialYearAlreadyExist(newFromDate, currentUser.getCompanyId())) {
                Integer newFinancialYearId = financialYearSetupService.creatFinancialYear(currentUser, newFromDate, newToDate);
                financialYearDTO.setFinancialYearId(newFinancialYearId);
            } else {
                financialYearSetupService.makeDefaultActiveYear(newFromDate, newToDate, currentUser);
            }
        }
        //endregion

        //create voucher count for selected company is not exists for current financial year --add by Bikash
        if (companyCreationDao.checkIsFinancialYearExistsForCompany(currentUser.getCompanyId(), financialYearDTO.getFinancialYearId())) {
            companyCreationDao.saveCompanyRelatedVoucherCountDetail(currentUser.getCompanyId(),
                    financialYearDTO.getFinancialYearId());
        }

        currentUser.setFinancialYearId(financialYearDTO.getFinancialYearId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(financialYearDTO.getFinancialYearFrom());
        Date fromDate = calendar.getTime();

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(financialYearDTO.getFinancialYearTo());
        Date toDate = calendarTo.getTime();

        currentUser.setFinancialYearFrom(DateUtil.format(fromDate, DateUtil.DD_MMM_YYYY));
        currentUser.setFinancialYearTo(DateUtil.format(toDate, DateUtil.DD_MMM_YYYY));
        currentUser.setCompanyAdd(companyCreationDTO.getMailingAddress());
        currentUser.setEmail(companyCreationDTO.getEmail());
        currentUser.setContact(companyCreationDTO.getMobileNo());
        currentUser.setMailingAddress(companyCreationDTO.getMailingAddress());
        currentUser.setBusinessType(companyCreationDTO.getBusinessType());
        currentUser.setUserId(userDTO.getUserId());
        request.getSession().setAttribute("currentUser", currentUser);
        modelAndView.setViewName("home");
        return modelAndView;
    }


    @RequestMapping(value = { "redirectHome"})
    public ModelAndView redirectHome(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/getTotalSale", method = RequestMethod.GET)
    public CompanyCreationDTO getTotalSale(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyCreationService.getTotalSale(currentUser.getCompanyId(), currentUser.getFinancialYearId());
    }

    @ResponseBody
    @RequestMapping(value = "/getScreenList", method = RequestMethod.GET)
    public List<DropdownDTO> getScreenList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyCreationService.getScreenList(currentUser.getUserId());

    }

}