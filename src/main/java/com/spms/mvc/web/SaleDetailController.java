package com.spms.mvc.web;

import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.SaleDetailService;
import com.spms.mvc.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 3/27/2021.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/saleDetail")
public class SaleDetailController {


    @Autowired
    private SaleDetailService saleDetailService;

    @Autowired
    private SaleItemService saleItemService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        DateUtil.fromTODateModel(currentUser, model);
        return "saleDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/getSaleList", method = RequestMethod.GET)
    public List<SaleItemListDTO> getSaleItemDetailList(HttpServletRequest request, Date fromDate, Date toDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return saleDetailService.getSaleItemDetailList(currentUser.getCompanyId(), fromDate, toDate);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSaleRelatedVoucher", method = RequestMethod.GET)
    public ResponseMessage deleteSaleRelatedVoucher(HttpServletRequest request, String receiptMemoNo) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return saleDetailService.deleteSaleRelatedVoucher(receiptMemoNo, currentUser);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, String receiptMemoNo, Integer voucherNo, RedirectAttributes redirectAttributes) throws ParseException {
        redirectAttributes.addFlashAttribute("voucherNo", voucherNo);
        redirectAttributes.addFlashAttribute("receiptMemoNo", receiptMemoNo);

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        if (currentUser.getBusinessType().equals(BusinessType.Construction.getValue()) || currentUser.getBusinessType().equals(BusinessType.Manufacturing.getValue())) {
            return "redirect:/stockIssue";
        }
        return "redirect:/saleItem";
    }

    @ResponseBody
    @RequestMapping(value = "/getSaleDetailView", method = RequestMethod.GET)
    public SaleItemDTO getSaleDetailView(HttpServletRequest request, Integer voucherNo) {
        return saleItemService.getSaleDetail((CurrentUser) request.getSession()
                .getAttribute("currentUser"), voucherNo);
    }
}
