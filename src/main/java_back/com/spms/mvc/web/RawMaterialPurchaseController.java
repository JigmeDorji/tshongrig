package com.spms.mvc.web;

import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.LocationSetUpService;
import com.spms.mvc.service.MoneyReceiptService;
import com.spms.mvc.service.SupplierSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 9/17/2021.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/rawMaterialPurchase")
public class RawMaterialPurchaseController {
    @Autowired
    private AddItemService addItemService;

    @Autowired
    private LocationSetUpService locationSetUpService;

    @Autowired
    private SupplierSetupService supplierSetupService;

    @Autowired
    private MoneyReceiptService moneyReceiptService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        List<DropdownDTO> locationList = locationSetUpService.getLocationSetUpList(currentUser);
        model.addAttribute("locationList", locationList);

//        model.addAttribute("itemCode", addItemService.generateItemCode());

        List<DropdownDTO> supplierList = supplierSetupService.getSupplierListDropDown(currentUser);
        model.addAttribute("supplierList", supplierList);


        Date newDate = new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);
        model.addAttribute("date", date);
//        model.addAttribute("brandList", addItemService.getBrandList());
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        model.addAttribute("unitList", addItemService.getUnitList());
        return "rawMaterialPurchase";
    }

}
