package com.spms.mvc.web;

import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.dto.RawMaterialStorageDTO;
import com.spms.mvc.dto.RawMaterialStorageViewDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.service.RawMaterialStorageService;
import com.spms.mvc.service.ViewItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/viewItemsForRawMaterial")
public class ViewItemsForRawMaterialController {

//    @Autowired
//    private ViewItemService viewItemService;
    @Autowired
    private RawMaterialStorageService rawMaterialStorageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) throws ParseException {

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        DateUtil.fromTODateModel(currentUser,model);


        Date asDate= new SimpleDateFormat("dd-MMM-yyyy").parse("01-Jan-2022");


        List<RawMaterialStorageViewDTO> getList=rawMaterialStorageService.getList(currentUser,asDate);
        model.addAttribute("getList",getList);




        for (RawMaterialStorageViewDTO rawMaterialStorageViewDTO : getList) {
            System.out.println(rawMaterialStorageViewDTO);
        }

        return "viewItemsForRawMaterial";


    }

    @ResponseBody
    @RequestMapping(value = "/getItemAvailable", method = RequestMethod.GET)
    public List<RawMaterialStorageViewDTO> getItemAvailable(HttpServletRequest request, Date asOnDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return rawMaterialStorageService.getList(currentUser,asOnDate);
    }

}
