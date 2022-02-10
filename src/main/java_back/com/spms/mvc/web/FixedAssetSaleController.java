package com.spms.mvc.web;

import com.spms.mvc.dto.OpeningAndBuyingDTO;
import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.FixedAssetSaleService;
import com.spms.mvc.service.MoneyReceiptService;
import com.spms.mvc.service.SaleItemService;
import com.spms.mvc.service.ViewPrintCopyService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 9/16/2021.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/fixedAssetSale")
public class FixedAssetSaleController extends BaseController {
    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private SaleItemService saleItemService;

    @Autowired
    private FixedAssetSaleService fixedAssetSaleService;

    @Autowired
    private ViewPrintCopyService viewPrintCopyService;


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String index(HttpServletRequest request, Model model) {
        CurrentUser currentUser = getCurrentUser(request);
        Date newDate = new Date();
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(newDate);
        model.addAttribute("date", date);
        model.addAttribute("companyName", currentUser.getCompanyName());
        model.addAttribute("companyAdd", currentUser.getCompanyAdd());
        model.addAttribute("contact", currentUser.getContact());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        return "fixedAssetSale";
    }


    @ResponseBody
    @RequestMapping(value = "/getFaItemDetails", method = RequestMethod.GET)
    public List<OpeningAndBuyingDTO> getFaItemDetails(HttpServletRequest request, BigInteger assetDetailId) {
        return fixedAssetSaleService.getFaItemDetails(assetDetailId, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/saveSaleItemDetails", method = RequestMethod.POST)
    public ResponseMessage saveItemDetails(HttpServletRequest request, SaleItemDTO saleItemDTO) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return fixedAssetSaleService.saveSaleItemDetails(saleItemDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/generateReceipt", method = RequestMethod.GET)
    public List<SaleItemListDTO> generateReceipt(HttpServletRequest request, String receiptMemoNo) throws JRException, IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return saleItemService.generateReceipt(receiptMemoNo);
    }

    @ResponseBody
    @RequestMapping(value = "/displayPDFFile/{pdfFileName}", produces = "application/pdf", method = RequestMethod.GET)
    public HttpEntity<byte[]> generateServiceReceived(@PathVariable("pdfFileName") String pdfFileName) throws IOException {
        return viewPrintCopyService.displayPDFFile(pdfFileName);
    }

    /**
     * generate the beneficiary order Number
     *
     * @return String
     */
    private String getRandNo() {
        String date = (new SimpleDateFormat("dd-MM-yyyy-").format(new Date())) + (new SimpleDateFormat("HHmmss").format(new
                Date()));
        StringBuilder generatedToken = new StringBuilder();
        return generatedToken.toString().concat(date);
    }

    @ResponseBody
    @RequestMapping(value = "/getSaleDetail", method = RequestMethod.GET)
    public SaleItemDTO getSaleDetail(HttpServletRequest request, Integer voucherNo) {
        return saleItemService.getSaleDetail((CurrentUser) request.getSession()
                .getAttribute("currentUser"), voucherNo);
    }

    @ResponseBody
    @RequestMapping(value = "/getAvailableQty", method = RequestMethod.GET)
    public BigDecimal getAvailableQty(HttpServletRequest request, String itemCode) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return saleItemService.getAvailableQty(itemCode, currentUser.getCompanyId());
    }


    @ResponseBody
    @RequestMapping(value = "/getItemList", method = RequestMethod.GET)
    public List<DropdownDTO> getIteList(HttpServletRequest request) {
        return fixedAssetSaleService.getItemList((CurrentUser) request.getSession()
                .getAttribute("currentUser"));
    }

    @ResponseBody
    @RequestMapping(value = "/getItemCodeList", method = RequestMethod.GET)
    public List<DropdownDTO> getItemCodeList(BigInteger assetDetailId) {
        return fixedAssetSaleService.getItemCodeList(assetDetailId);
    }

}
