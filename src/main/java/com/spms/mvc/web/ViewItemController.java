package com.spms.mvc.web;

import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.ViewItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 15-Dec-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/viewItem")
public class ViewItemController {

    @Autowired
    private ViewItemService viewItemService;

    @Autowired
    private AddItemService addItemService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("brandList", addItemService.getBrandList(currentUser));
        DateUtil.fromTODateModel(currentUser,model);
//        model.addAttribute("totalStockBalAmt",df2.format(viewItemService.getTotalStockBal(currentUser)));
        return "viewItem";
    }


    @ResponseBody
    @RequestMapping(value = "/getItemAvailable", method = RequestMethod.GET)
    public List<PurchaseDTO> getItemAvailable(HttpServletRequest request, Date asOnDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return viewItemService.getItemAvailable(currentUser,asOnDate);
    }

    @RequestMapping(value = "/navigateToDetail", method = RequestMethod.GET)
    public String navigateToDetail(HttpServletRequest request,String itemCode, Date asOnDate, RedirectAttributes redirectAttributes) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        redirectAttributes.addFlashAttribute("itemCode", itemCode);
        redirectAttributes.addFlashAttribute("asOnDate", DateUtil.format(asOnDate, DateUtil.DD_MMM_YYYY));
        redirectAttributes.addFlashAttribute("itemName", viewItemService.getItemName(itemCode,currentUser));
        return "redirect:/viewItemDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/viewBrandWiseItemDetail", method = {RequestMethod.GET})
    public ModelAndView viewBrandWiseItemDetail(HttpServletRequest request, Integer brandId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return viewItemService.viewBrandWiseItemDetail(brandId, currentUser.getCompanyId(),currentUser.getLoginId());
    }
}
