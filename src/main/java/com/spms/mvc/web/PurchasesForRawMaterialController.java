package com.spms.mvc.web;


import com.spms.mvc.dto.*;
import com.spms.mvc.dto.PurchasesForRawMaterialSaveDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/purchasesForRawMaterial")
public class PurchasesForRawMaterialController {

    @Autowired
    private RawMaterialLocationSetupService rawMaterialLocationSetupService;

    @Autowired
    private AddItemService addItemService;

    @Autowired
    private SupplierSetupService supplierSetupService;

    @Autowired
    private MoneyReceiptService moneyReceiptService;



    @RequestMapping(value = "", method = RequestMethod.GET)

    public String list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        List<DropdownDTO> supplierList = supplierSetupService.getSupplierListDropDown(currentUser);
        model.addAttribute("supplierList", supplierList);

        List<DropdownDTO> rawMaterialLocationSetupList = rawMaterialLocationSetupService.getLocationSetUpList(currentUser);
        model.addAttribute("rawMaterialLocationSetupList", rawMaterialLocationSetupList);


        Date newDate = new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);
        model.addAttribute("date", date);


        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        model.addAttribute("unitList", addItemService.getUnitList());



        return "purchasesForRawMaterial";
    }

    @Autowired
    private  PurchasesForRawMaterialService purchasesForRawMaterialService;

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseMessage saveTheRawMaterialFromPurchases( HttpServletRequest request,PurchasesForRawMaterialSaveDTO purchasesForRawMaterialSaveDTO,RawMaterialStorageDTO rawMaterialStorageDTO) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return purchasesForRawMaterialService.save(request,currentUser,purchasesForRawMaterialSaveDTO,rawMaterialStorageDTO);

    }







}
