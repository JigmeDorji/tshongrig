package com.spms.mvc.web;

import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.ViewItemDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 8/25/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/viewItemDetail")
public class ViewItemDetailController {

    @Autowired
    private ViewItemDetailService viewItemDetailService;

    @RequestMapping(value = "", method =     RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        return "viewItemDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/getItemDetail", method = RequestMethod.GET)
    public List<PurchaseDTO> getItemAvailable(String itemCode,Date asOnDate, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return viewItemDetailService.getItemDetail(currentUser.getCompanyId(), itemCode, currentUser.getFinancialYearId(),asOnDate);
    }

    @RequestMapping(value = "/navigateToPurchasePage", method = RequestMethod.GET)
    public String navigateToDetail(Integer purchaseId, String purchaseDate, Integer voucherNo, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("purchaseId", purchaseId);
        redirectAttributes.addFlashAttribute("purchaseDate", purchaseDate);
        redirectAttributes.addFlashAttribute("purchaseVoucherNo", voucherNo);
        return "redirect:/receivedItem";
    }

    @RequestMapping(value = "/navigateToPurchaseOpeningPage", method = RequestMethod.GET)
    public String navigateToPurchaseOpeningPage(Integer purchaseId, String purchaseDate, Integer voucherNo, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("purchaseId", purchaseId);
        redirectAttributes.addFlashAttribute("purchaseDate", purchaseDate);
        redirectAttributes.addFlashAttribute("purchaseVoucherNumber", voucherNo);
        return "redirect:/openingBalanceInventory";
    }

    @RequestMapping(value = "/navigateToSalePage", method = RequestMethod.GET)
    public String navigateToSalePage(Integer voucherNo, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("voucherNo", voucherNo);
        return "redirect:/saleItem";
    }

    @RequestMapping(value = "/navigateToReturnItem", method = RequestMethod.GET)
    public String navigateToReturnItem(Integer purchaseId, String itemCode, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("itemCode", itemCode);
        return "redirect:/returnItem";
    }

    @ResponseBody
    @RequestMapping(value = "/deletePurchaseRelatedVoucher", method = RequestMethod.GET)
    public ResponseMessage deletePurchaseRelatedVoucher(HttpServletRequest request, Integer voucherNo, String itemCode, BigDecimal qty) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return viewItemDetailService.deletePurchaseRelatedVoucher(voucherNo, itemCode, qty, currentUser);
    }


}
