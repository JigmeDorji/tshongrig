package com.spms.mvc.web;

import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.ReturnItemService;
import com.spms.mvc.service.SaleItemService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by jigmePc on 8/31/2019.
 */

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/returnItem")
public class ReturnItemController {
    @Autowired
    private ReturnItemService returnItemService;

    @Autowired
    private SaleItemService saleItemService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        return "returnItem";
    }

    @ResponseBody
    @RequestMapping(value = "/getReceiptItemDetails", method = RequestMethod.GET)
    public List<SaleItemListDTO> getReceiptItemDetails(String receiptNo) {
        return returnItemService.getReceiptItemDetails(receiptNo);
    }

    @ResponseBody
    @RequestMapping(value = "/saveReturnQty", method = RequestMethod.POST)
    public ResponseMessage saveReturnQty(HttpServletRequest request, SaleItemDTO saleItemDTO) throws JRException, IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return returnItemService.saveReturnQty(saleItemDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getItemDetails", method = RequestMethod.GET)
    public List<SaleItemDTO> getItemDetails(HttpServletRequest request,String itemCode) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return saleItemService.getItemDetails(itemCode, currentUser);
    }

}