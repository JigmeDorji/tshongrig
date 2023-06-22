package com.spms.mvc.web;

import com.spms.mvc.dto.BrandDTO;
import com.spms.mvc.dto.PurchaseCallingDTO;
import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 12-Dec-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/receivedItem")
public class ReceivedItemController {

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

        model.addAttribute("currentUser", currentUser);

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
        return "receivedItem";
    }


    @ResponseBody
    @RequestMapping(value = "/getSupplierDropdownList", method = RequestMethod.GET)
    public List<DropdownDTO> getSupplierList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return supplierSetupService.getSupplierListDropDown(currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(PurchaseCallingDTO purchaseCallingDTO, HttpServletRequest request) throws
            IOException, ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        return addItemService.save(purchaseCallingDTO, currentUser);
    }


    @ResponseBody
    @RequestMapping(value = "/saveBrandDetail", method = RequestMethod.POST)
    public ResponseMessage saveBrandDetail(BrandDTO brandDTO, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.saveBrandDetail(brandDTO, currentUser);
    }


    @ResponseBody
    @RequestMapping(value = "/getPurchaseDetail", method = RequestMethod.GET)
    public ResponseMessage getPurchaseDetail(HttpServletRequest request, Integer purchaseId, String purchaseDate, Integer voucherNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.getPurchaseDetail(purchaseId, DateUtil.toDate(purchaseDate, DateUtil.DD_MMM_YYYY), currentUser, voucherNo);
    }

    @ResponseBody
    @RequestMapping(value = "/getOpeningPurchaseDetail", method = RequestMethod.GET)
    public PurchaseDTO getOpeningPurchaseDetail(HttpServletRequest request, Integer purchaseId, String purchaseDate, Integer voucherNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        return addItemService.getOpeningPurchaseDetail(purchaseId, DateUtil.toDate(purchaseDate, DateUtil.DD_MMM_YYYY), voucherNo);
    }


    @ResponseBody
    @RequestMapping(value = "/getRecentPurchaseData", method = RequestMethod.GET)
    public PurchaseDTO getRecentPurchaseData() {
        return addItemService.getRecentPurchaseData();
    }

    @ResponseBody
    @RequestMapping(value = "/checkItemName", method = RequestMethod.GET)
    public ResponseMessage checkItemName(String itemName) {
        return addItemService.checkItemName(itemName);
    }

    @ResponseBody
    @RequestMapping(value = "/getSlNo", method = RequestMethod.GET)
    public PurchaseDTO getSlNo(Integer brandId) {
        return addItemService.getSlNo(brandId);
    }

    @ResponseBody
    @RequestMapping(value = "/getSINo", method = RequestMethod.POST)
    public Integer getSINo(HttpServletRequest request, Integer companyId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.getSINo(currentUser);
    }


    @ResponseBody
    @RequestMapping(value = "/getItemDetails", method = RequestMethod.GET)
    public PurchaseDTO getItemDetails(HttpServletRequest request, String itemCode) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.getItemDetails(itemCode, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getItemDetailsByPartNo", method = RequestMethod.GET)
    public PurchaseDTO getItemDetailsByPartNo(String partNo) {
        return addItemService.getItemDetailsByPartNo(partNo);
    }


    @ResponseBody
    @RequestMapping(value = "/getBrandList", method = RequestMethod.GET)
    public List<DropdownDTO> getBrandList(HttpServletRequest request) {
        return addItemService.getBrandList((CurrentUser) request.getSession()
                .getAttribute("currentUser"));
    }


    @ResponseBody
    @RequestMapping(value = "/getTypeDetail", method = RequestMethod.GET)
    public List<DropdownDTO> getTypeDetail(HttpServletRequest request) {
        return addItemService.getTypeDetail((CurrentUser) request.getSession()
                .getAttribute("currentUser"));
    }


    @ResponseBody
    @RequestMapping(value = "/getItemCodeListByBrandId", method = RequestMethod.GET)
    public List<DropdownDTO> getItemCodeListByBrandId(HttpServletRequest request, Integer brandId) {
        return addItemService.getItemCodeListByBrandId((CurrentUser) request.getSession()
                .getAttribute("currentUser"), brandId);
    }

    @ResponseBody
    @RequestMapping(value = "/checkPurchaseInvoiceNoAlreadyEntered", method = RequestMethod.GET)
    public ResponseMessage checkPurchaseInvoiceNoAlreadyEntered(String itemCode, String purchaseInvoiceNo) {
        return addItemService.checkPurchaseInvoiceNoAlreadyEntered(itemCode, purchaseInvoiceNo);
    }

    @ResponseBody
    @RequestMapping(value = "/getItemListByInvoiceNo", method = RequestMethod.GET)
    public ResponseMessage getItemListByInvoiceNo(HttpServletRequest request, String purchaseInvoiceNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.getItemListByInvoiceNo(purchaseInvoiceNo, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/updateItemSerialNumber", method = RequestMethod.GET)
    public ResponseMessage updateItemSerialNumber(HttpServletRequest request, String brandName) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.updateItemSerialNumber(brandName, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAllThePurchaseEntry", method = RequestMethod.GET)
    public ResponseMessage deleteAllThePurchaseEntry(HttpServletRequest request, Integer purchaseVoucherNo, String purchaseInvoiceNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return addItemService.deleteAllThePurchaseEntry(purchaseVoucherNo, currentUser, purchaseInvoiceNo);
    }

}

