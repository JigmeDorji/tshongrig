package com.spms.mvc.web;

import com.spms.mvc.dto.LocationSetUpDTO;
import com.spms.mvc.dto.RawMaterialLocationSetupDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.RawMaterialLocationSetupService;
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

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/rawMaterialLocationSetup")
public class RawMaterialLocationSetupController {

    @Autowired
    private RawMaterialLocationSetupService rawMaterialLocationSetupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        return "rawMaterialLocationSetup";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, RawMaterialLocationSetupDTO rawMaterialLocationSetupDTO) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return rawMaterialLocationSetupService.save(rawMaterialLocationSetupDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getLocationList", method = RequestMethod.GET)
    public List<LocationSetUpDTO> getItemCategoryList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return rawMaterialLocationSetupService.getLocationList(currentUser.getCompanyId());
    }

}
