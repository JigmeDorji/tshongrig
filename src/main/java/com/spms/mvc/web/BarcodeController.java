package com.spms.mvc.web;

import com.spms.mvc.dto.SaleRecordDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.BarcodeService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SonamPC on 20-Dec-16.
 */

@Controller
@RequestMapping(value = "/barcode")
public class BarcodeController {
    @Autowired
    private AddItemService addItemService;
    @Autowired
    private BarcodeService barcodeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model, Authentication authentication) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<DropdownDTO> itemList = barcodeService.getItemList(currentUser);
        model.addAttribute("itemList", itemList);
        return "barcode";

    }


    @RequestMapping(value = "/generateBarcode")
    public ModelAndView generateBarcode(HttpServletRequest request, ModelAndView modelAndView, String itemCode, Integer qty) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<SaleRecordDTO> dtoList = barcodeService.getItemCodeList(itemCode, currentUser, qty);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dtoList, false);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("datasource", jrDataSource);
        modelAndView = new ModelAndView("barcodeHtml", params);
        return modelAndView;
    }
}

