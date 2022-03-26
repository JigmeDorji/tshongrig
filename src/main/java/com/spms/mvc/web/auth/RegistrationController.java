package com.spms.mvc.web.auth;

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
 * Created by jigme.dorji on 3/20/2022.
 */
@Controller
//@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/registration")
public class RegistrationController {
    @Autowired
    CompanyCreationService companyCreationService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("businessTypeList", companyCreationService.getBusinessTypeDropdown());
        return "registration";
    }

    @ResponseBody
    @RequestMapping(value = "/saveCompanyDetails", method = RequestMethod.POST)
    public ResponseMessage saveCompanyDetails(CompanyCreationDTO companyCreationDTO,
                                              HttpServletRequest request){
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyCreationService.saveCompanyDetails(companyCreationDTO, currentUser,true);
    }

}
