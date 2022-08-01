package com.spms.mvc.web;

import com.spms.mvc.Enumeration.SystemDataInt;
import com.spms.mvc.dto.CompanyCreationDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.CompanyCreationService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/3/2019.
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/companyCreation")
public class CompanyCreationController extends BaseController {

    @Autowired
    private CompanyCreationService companyCreationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        List<DropdownDTO> businessTypeList = companyCreationService.getBusinessTypeDropdown();
        model.addAttribute("businessTypeList", businessTypeList);
        return "companyCreation";
    }

    @ResponseBody
    @RequestMapping(value = "/getCompanyDetailList", method = RequestMethod.GET)
    public List<CompanyCreationDTO> getCompanyDetailList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyCreationService.getCompanyDetailList(currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/selectCompany", method = RequestMethod.GET)
    public ResponseMessage selectCompany(HttpServletRequest request,Integer companyId) {
        ResponseMessage responseMessage= new ResponseMessage();
        CurrentUser currentUser=getCurrentUser(request);
        CompanyCreationDTO companyCreationDTO = companyCreationService.getSelectedCompanyDetails(companyId);
       if(companyCreationDTO.getStatus()==null){
           responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
           responseMessage.setText("Company is in pending state.");
           return responseMessage;
       }
        FinancialYearDTO financialYearDTO = companyCreationService.getCurrentFinancialYearIdByCompany(companyId);

        currentUser.setCompanyName(companyCreationDTO.getCompanyName());
        currentUser.setCompanyId(companyCreationDTO.getCompanyId());


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
        currentUser.setUserId(currentUser.getUserId());
        request.getSession().setAttribute("currentUser", currentUser);


        responseMessage.setText("Successfully Selected");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @ResponseBody
    @RequestMapping(value = "/populateCompanyDetail", method = RequestMethod.GET)
    public CompanyCreationDTO populateCompanyDetail(Integer companyId) {
        return companyCreationService.populateCompanyDetail(companyId);
    }

    @ResponseBody
    @RequestMapping(value = "/saveCompanyDetails", method = RequestMethod.POST)
    public ResponseMessage saveCompanyDetails(CompanyCreationDTO companyCreationDTO,
                                              HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyCreationService.saveCompanyDetails(companyCreationDTO, currentUser,false);
    }


    @ResponseBody
    @RequestMapping(value = "/deleteCompanyDetails", method = RequestMethod.GET)
    public ResponseMessage deleteCompanyDetails(Integer companyId) {
        return companyCreationService.deleteCompanyDetails(companyId);
    }


}
