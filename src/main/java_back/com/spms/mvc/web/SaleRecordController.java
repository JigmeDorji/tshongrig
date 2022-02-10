package com.spms.mvc.web;

import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.SaleRecordService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SonamPC on 18-Dec-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/saleRecord")
public class SaleRecordController {

    @Autowired
    private AddItemService addItemService;

    @Autowired
    private SaleRecordService saleRecordService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        //List<DropdownDTO> itemCategoryList = addItemService.getItemCategory();
//        model.addAttribute("itemCategoryList", itemCategoryList);
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("permissionType",currentUser.getPermissionType());
        return "saleRecord";
    }

    @RequestMapping(value = "/generateReport")
    public ModelAndView generateBarcode(HttpServletRequest request, ModelAndView modelAndView,
                                        Date fromDate, Date toDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<SaleItemDTO> saleItemDTOs = saleRecordService.getSaleRecordListSummary(fromDate, toDate);
        Map<String, Object> params = new HashMap<String, Object>();
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(saleItemDTOs, false);
        params.put("datasource", jrDataSource);
        params.put("printedDate", new Date());
        params.put("userName", currentUser.getTxtUserName());
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        params.put("memoTitle", "Sale Report");
        params.put("companyName", currentUser.getCompanyName());
        params.put("totalDiscount", saleRecordService.getTotalDiscount(fromDate, toDate,currentUser));
        params.put("totalAmtBank", saleRecordService.getTotalAmtBank(fromDate, toDate,currentUser));
        params.put("totalAmtCash", saleRecordService.getTotalCash(fromDate, toDate,currentUser));
        params.put("totalAmtCredit", saleRecordService.getTotalCredit(fromDate, toDate,currentUser));
        params.put("totalAmtInvoiceSale", saleRecordService.getTotalInvoiceSale(fromDate, toDate,currentUser));
        params.put("saleReplaceDiffAmt", saleRecordService.getSaleReplaceDiffAmt(fromDate, toDate));
        modelAndView = new ModelAndView("summarySaleReportHTML", params);
        return modelAndView;
    }
}
