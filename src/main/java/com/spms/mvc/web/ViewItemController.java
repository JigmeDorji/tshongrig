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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 15-Dec-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/viewItem")
public class ViewItemController {

    @Autowired
    private ViewItemService viewItemService;

    @Autowired
    private AddItemService addItemService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {

        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("brandList", addItemService.getBrandList(currentUser));
        DateUtil.fromTODateModel(currentUser, model);
//        model.addAttribute("totalStockBalAmt",df2.format(viewItemService.getTotalStockBal(currentUser)));
        return "viewItem";
    }


    @ResponseBody
    @RequestMapping(value = "/getItemAvailable", method = RequestMethod.GET)
    public List<PurchaseDTO> getItemAvailable(HttpServletRequest request, Date asOnDate) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return viewItemService.getItemAvailable(currentUser, asOnDate);
    }

    @RequestMapping(value = "/navigateToDetail", method = RequestMethod.GET)
    public String navigateToDetail(HttpServletRequest request, String itemCode, Date asOnDate, RedirectAttributes redirectAttributes) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        redirectAttributes.addFlashAttribute("itemCode", itemCode);
        redirectAttributes.addFlashAttribute("asOnDate", DateUtil.format(asOnDate, DateUtil.DD_MMM_YYYY));
        redirectAttributes.addFlashAttribute("itemName", viewItemService.getItemName(itemCode, currentUser));
        return "redirect:/viewItemDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/viewBrandWiseItemDetail", method = {RequestMethod.GET})
    public ModelAndView viewBrandWiseItemDetail(HttpServletRequest request, Integer brandId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return viewItemService.viewBrandWiseItemDetail(brandId, currentUser.getCompanyId(), currentUser.getLoginId());
    }


    @RequestMapping(value = "/exportAllItemsToExcel", method = RequestMethod.GET)
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
        headerRow.createCell(1).setCellValue("Part Number");
        headerRow.createCell(2).setCellValue("Item Coder");
        headerRow.createCell(3).setCellValue("Location ");
        headerRow.createCell(4).setCellValue("Qty");
        headerRow.createCell(5).setCellValue("Unit");
        headerRow.createCell(6).setCellValue("Selling Price");

        // create data rows
        int rowNum = 1;
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((i + 1));
            row.createCell(1).setCellValue(data.get(i).getItemName());
            row.createCell(2).setCellValue(data.get(i).getItemCode());
            row.createCell(3).setCellValue(data.get(i).getLocationId());
            row.createCell(4).setCellValue(String.valueOf(data.get(i).getQtyBig()));
            row.createCell(5).setCellValue(data.get(i).getUnitName());
            row.createCell(6).setCellValue(data.get(i).getSellingPrice());
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
