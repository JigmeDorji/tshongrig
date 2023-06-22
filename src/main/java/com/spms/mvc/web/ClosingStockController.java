package com.spms.mvc.web;


import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.service.AddItemService;
import com.spms.mvc.service.ViewItemService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/closingStock")
public class ClosingStockController {


    @Autowired
    private ViewItemService viewItemService;

    @Autowired
    private AddItemService addItemService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("brandList", addItemService.getBrandList(currentUser));

        model.addAttribute("currentUser", currentUser);
        DateUtil.fromTODateModel(currentUser, model);

//        model.addAttribute("totalStockBalAmt",df2.format(viewItemService.getTotalStockBal(currentUser)));
        return "closingStock";
    }


    @ResponseBody
    @RequestMapping(value = "/getItemAvailable", method = RequestMethod.GET)
    public List<PurchaseDTO> getItemAvailable(HttpServletRequest request, Date asOnDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        List<PurchaseDTO> result = viewItemService.getItemAvailable(currentUser, asOnDate);
        List<PurchaseDTO> sortedArray = new ArrayList<>();
        for (int integer = 0; integer < result.size(); integer++) {

            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setSerialNo(String.valueOf(integer+1));
            purchaseDTO.setItemCode(result.get(integer).getItemCode());
            purchaseDTO.setItemName(result.get(integer).getItemName());
            purchaseDTO.setLocationId(result.get(integer).getLocationId());
            purchaseDTO.setQtyBig(result.get(integer).getQtyBig());
            purchaseDTO.setUnitName(result.get(integer).getUnitName());
            purchaseDTO.setCostPrice(result.get(integer).getCostPrice());
            purchaseDTO.setClosingStockAmount(result.get(integer).getQtyBig().multiply(BigDecimal.valueOf(result.get(integer).getCostPrice())));
            sortedArray.add(purchaseDTO);
        }

        return sortedArray;
    }

    @RequestMapping(value = "/exportClosingItems", method = RequestMethod.GET)
    public ModelAndView exportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // create a list of data to be exported

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        List<PurchaseDTO> data = viewItemService.getItemAvailable(currentUser, new Date());

        // create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Item Detail List");
        // create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("SI.NO");
        headerRow.createCell(1).setCellValue("Item Code");
        headerRow.createCell(2).setCellValue("Item Name");
        headerRow.createCell(3).setCellValue("Location ");
        headerRow.createCell(5).setCellValue("Unit");
        headerRow.createCell(4).setCellValue("Qty");
        headerRow.createCell(6).setCellValue("Cost Price");
        headerRow.createCell(7).setCellValue("Amount");


//        SI.NO	Item Code	Item Name	Location	Qty	Cost PRice		Amount
        // create data rows
        int rowNum = 1;
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((i + 1));
            row.createCell(1).setCellValue(data.get(i).getItemCode());
            row.createCell(2).setCellValue(data.get(i).getItemName());
            row.createCell(3).setCellValue(data.get(i).getLocationId());
            row.createCell(4).setCellValue(data.get(i).getUnitName());
            row.createCell(5).setCellValue(String.valueOf(data.get(i).getQtyBig()));
            row.createCell(6).setCellValue(data.get(i).getCostPrice());
            row.createCell(7).setCellValue(String.valueOf(data.get(i).getQtyBig().multiply(BigDecimal.valueOf(data.get(i).getCostPrice()))));
        }

        // set the response content type and headers
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + "Exported All Items" + ".xlsx\"");

        // write the workbook to the response output stream
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();

        // return null to indicate that the response has been handled
        return null;
    }
}
