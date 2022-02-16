package com.spms.mvc.web;

import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.BOQRABillGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

/**
 * Description: RABillDetailController
 * Date:  2022-Jan-21
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-21
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/raBillDetail")
public class BOQRABillDetailController {

    @Autowired
    private BOQRABillGenerationService boqRABillGenerationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        return "raBillDetail";
    }


    @ResponseBody
    @RequestMapping(value = "/getGeneratedRAList", method = RequestMethod.GET)
    public List<BOQDetailsDTO> getGeneratedRAList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return boqRABillGenerationService.getGeneratedRAList(currentUser.getCompanyId());
    }

    @RequestMapping(value = "/editRaBillDetail", method = RequestMethod.GET)
    public String editRaBillDetail(RedirectAttributes redirectAttributes, BigInteger boqId, Integer raSerialNo,Integer voucherNo) {
        redirectAttributes.addFlashAttribute("boqId", boqId);
        redirectAttributes.addFlashAttribute("raSerialNo", raSerialNo);
        redirectAttributes.addFlashAttribute("voucherNo", voucherNo);
        return "redirect:/raBillGeneration";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteRaBillDetail", method = RequestMethod.GET)
    public ResponseMessage deleteRaBillDetail(HttpServletRequest request, BigInteger boqId, Integer raSerialNo, Integer voucherNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return boqRABillGenerationService.deleteRaBillDetail(boqId, raSerialNo, voucherNo, currentUser);
    }

}
