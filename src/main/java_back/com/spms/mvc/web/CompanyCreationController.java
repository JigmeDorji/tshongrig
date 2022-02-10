package com.spms.mvc.web;

import com.spms.mvc.dto.CompanyCreationDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.CompanyCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/3/2019.
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/companyCreation")
public class CompanyCreationController {

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
    public List<CompanyCreationDTO> getCompanyDetailList() {
        return companyCreationService.getCompanyDetailList();
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
        return companyCreationService.saveCompanyDetails(companyCreationDTO, currentUser);
    }


    @ResponseBody
    @RequestMapping(value = "/deleteCompanyDetails", method = RequestMethod.GET)
    public ResponseMessage deleteCompanyDetails(Integer companyId) {
        return companyCreationService.deleteCompanyDetails(companyId);
    }


}
