package com.spms.mvc.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SonamPC on 19-Jun-17.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/viewStockValue")
public class ViewStockValueController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        /*List<DropdownDTO> itemCategoryList = addItemService.getItemCategory();
        model.addAttribute("itemCategoryList", itemCategoryList);*/
        Date newDate =  new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);
        model.addAttribute("date", date);
        return "viewStockValue";
    }
}
