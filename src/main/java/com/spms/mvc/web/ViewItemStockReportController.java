package com.spms.mvc.web;

import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.ViewItemService;
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
import java.util.Map;

/**
 * Created by SonamPC on 28-Apr-18. viewItemStockReport
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/viewItemStockReport")
public class ViewItemStockReportController {

    @Autowired
    private AddItemService addItemService;
    @Autowired
    private ViewItemService viewItemService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        return "viewItemStockReport";
    }


    @RequestMapping(value = "/generateStockReport")
    public ModelAndView generateBarcode(HttpServletRequest request, ModelAndView modelAndView,Date fromDate, Date toDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
//        List<ViewItemDTO> itemAvailable = viewItemService.generateBarcode();
//        JRDataSource jrDataSource = new JRBeanCollectionDataSource(itemAvailable, false);
        Map<String, Object> parameterMap = new HashMap<String, Object>();
//        parameterMap.put("datasource", jrDataSource);
        parameterMap.put("headerName", "Kinga  Auto Mobile");
        parameterMap.put("postNo", "Post box No:992");
        parameterMap.put("contactDetails", "Tele:02-351449 | Tele Fax:351609");
        parameterMap.put("addresses", "Olakha Thimphu,Bhutan");
        parameterMap.put("emailAddresses", "meetshering@gmail.com");
        parameterMap.put("receiptName", "Cash Memo");
        parameterMap.put("printedDate", new Date());
        parameterMap.put("userName", currentUser.getTxtUserName());
        parameterMap.put("memoTitle", "Stock Details Report");

        modelAndView = new ModelAndView("availableStockReport", parameterMap);
        return modelAndView;
    }
}
