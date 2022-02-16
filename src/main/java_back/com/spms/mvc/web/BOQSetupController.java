package com.spms.mvc.web;

import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.dto.BOQDetailsListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.BOQSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * Description: BOQOperationSetupController
 * Date:  2022-Jan-06
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-06
 * Change Description:
 * Search Tag:
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/boqSetup")
public class BOQSetupController {

    @Autowired
    private BOQSetupService boqSetupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        return "boqSetup";
    }

    @ResponseBody
    @RequestMapping(value = "/importExcelFile", method = RequestMethod.POST)
    public ResponseMessage importExcelFile(BOQDetailsDTO boqDetailsDTO) throws IOException {
        return boqSetupService.importExcelFile(boqDetailsDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/saveBOQDetail", method = RequestMethod.POST)
    public ResponseMessage saveBOQDetail(HttpServletRequest request, BOQDetailsDTO boqDetailsDTO) throws ParseException, IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return boqSetupService.save(boqDetailsDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByBOQId", method = RequestMethod.GET)
    public BOQDetailsDTO getDetailByBOQId(BigInteger boqId) {
        return boqSetupService.getDetailByBOQId(boqId);
    }

    @ResponseBody
    @RequestMapping(value = "/getBOQList", method = RequestMethod.GET)
    public List<BOQDetailsListDTO> getBOQList(BigInteger boqId) {
        return boqSetupService.getBOQList(boqId);
    }

}
