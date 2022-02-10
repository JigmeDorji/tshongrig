package com.spms.mvc.web;

import com.spms.mvc.dto.SupplierSetupDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.SupplierSetupService;
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
 * Created by jigmePc on 10/20/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/supplierSetup")
public class SupplierSetupController {

    @Autowired
    private SupplierSetupService supplierSetupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        return "supplierSetup";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save( HttpServletRequest request,SupplierSetupDTO supplierSetupDTO) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return supplierSetupService.save(supplierSetupDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getSupplierList", method = RequestMethod.GET)
    public List<SupplierSetupDTO> getSupplierList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return supplierSetupService.getSupplierList(currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/getSupplierDetails", method = RequestMethod.GET)
    public SupplierSetupDTO getSupplierDetails(Integer id) {
        return supplierSetupService.getSupplierDetails(id);
    }



}
