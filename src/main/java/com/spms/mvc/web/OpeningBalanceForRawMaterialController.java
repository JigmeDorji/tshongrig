package com.spms.mvc.web;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.BusinessType;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dao.RawMaterialStorageDao;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.RawMaterialLocationSetup;
import com.spms.mvc.library.helper.AccountingUtil;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/openingBalanceForRawMaterial")
public class OpeningBalanceForRawMaterialController {


    @Autowired
    private RawMaterialLocationSetupService rawMaterialLocationSetupService;

    @Autowired
    private AddItemService addItemService;

    @Autowired
    private RawMaterialStorageService rawMaterialStorageService;

    @Autowired
    PurchasesForRawMaterialService purchasesForRawMaterialService;







    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        Date newDate = new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);

        model.addAttribute("date", date);

        List<DropdownDTO> rawMaterialLocationSetupList = rawMaterialLocationSetupService.getLocationSetUpList(currentUser);

        model.addAttribute("rawMaterialLocationSetupList", rawMaterialLocationSetupList);

        model.addAttribute("unitList", addItemService.getUnitList());

        return "openingBalanceForRawMaterial";

    }

//    @ResponseBody
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public ResponseMessage save(HttpServletRequest request, RawMaterialStorageDTO rawMaterialStorageDTO) throws
//            IOException {
//        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
//        return rawMaterialStorageService.save(rawMaterialStorageDTO, currentUser);
//
//    }





}
