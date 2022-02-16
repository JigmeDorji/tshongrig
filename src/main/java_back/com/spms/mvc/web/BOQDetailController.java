package com.spms.mvc.web;

import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.BOQSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

/**
 * Description: BOQDetailController
 * Date:  2022-Jan-13
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-13
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/boqDetail")
public class BOQDetailController {

    @Autowired
    BOQSetupService boqSetupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "boqDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/getGeneratedBOQList", method = RequestMethod.GET)
    public List<BOQDetailsDTO> getGeneratedBOQList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return boqSetupService.getGeneratedBOQList(currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseMessage delete(BigInteger boqId) {
        return boqSetupService.delete(boqId);
    }

    @RequestMapping(value = "/generateRABill", method = RequestMethod.GET)
    public String generateRABill(RedirectAttributes redirectAttributes, BigInteger boqId) {
        redirectAttributes.addFlashAttribute("boqId", boqId);
        return "redirect:/raBillGeneration";
    }

    @RequestMapping(value = "/editBoqDetail", method = RequestMethod.GET)
    public String editBoqDetail(RedirectAttributes redirectAttributes, BigInteger boqId) {
        redirectAttributes.addFlashAttribute("boqId", boqId);
        return "redirect:/boqSetup";
    }
}
