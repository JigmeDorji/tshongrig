package com.spms.mvc.web;

import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.dto.BOQDetailsListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.NumberInWords;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.BOQRABillGenerationService;
import com.spms.mvc.service.BOQSetupService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: BOQRABillGeneration
 * Date:  2022-Jan-13
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-13
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/raBillGeneration")
public class BOQRABillGenerationController {

    @Autowired
    private BOQRABillGenerationService boqRABillGenerationService;

    @Autowired
    private BOQSetupService boqSetupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

        CurrentUser currentUser = (CurrentUser) request.getSession()
                .getAttribute("currentUser");

        model.addAttribute("workOrderList", boqRABillGenerationService
                .getWorkOrderList(currentUser.getCompanyId()));

        String date = new SimpleDateFormat("dd-MMM-yyyy").format(currentUser.getCreatedDate());
        model.addAttribute("date", date);

        return "raBillGeneration";
    }

    @ResponseBody
    @RequestMapping(value = "/getRaSerialNo", method = RequestMethod.GET)
    public Integer getRaSerialNo(Integer boqId) {
        return boqRABillGenerationService.getRASerialNo(boqId);
    }

    @ResponseBody
    @RequestMapping(value = "/getBOQList", method = RequestMethod.GET)
    public List<BOQDetailsListDTO> getBOQList(BigInteger boqId) {
        return boqRABillGenerationService.getBOQList(boqId);
    }


    @ResponseBody
    @RequestMapping(value = "/getBOQListForUpdate", method = RequestMethod.GET)
    public BOQDetailsDTO getBOQListForUpdate(BigInteger boqId, Integer raSerialNo) {
        return boqRABillGenerationService.getBOQListForUpdate(boqId, raSerialNo);
    }

    @ResponseBody
    @RequestMapping(value = "/saveRABillDetail", method = RequestMethod.POST)
    public ResponseMessage saveRABillDetail(HttpServletRequest request, BOQDetailsDTO boqDetailsDTO) throws ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return boqRABillGenerationService.saveRABillDetail(boqDetailsDTO, currentUser);
    }

    @RequestMapping(value = "/generateRABill")
    public ModelAndView generateRABill(HttpServletRequest request, BigInteger boqId, Integer raSerialNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        List<BOQDetailsListDTO> dtoList = boqRABillGenerationService.getRABillList(boqId, raSerialNo);
        BOQDetailsDTO boqDetailsDTO = boqSetupService.getDetailByBOQId(boqId);

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dtoList, false);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("datasource", jrDataSource);
        params.put("raBillNo", boqRABillGenerationService.getRABillNo(boqId, raSerialNo));
        params.put("workOrderNo", boqDetailsDTO.getWorkOrderNo());
        params.put("employingAgency", boqDetailsDTO.getEmployingAgency());
        params.put("nameOfWork", boqDetailsDTO.getNameOfWork());
        params.put("totalBillAmtInWords", NumberInWords.convert(calTotal(dtoList).longValue()));
        params.put("receiptDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("mailingAddress", currentUser.getMailingAddress());
        params.put("printedDate", new Date());
        params.put("userName", currentUser.getTxtUserName());
        return new ModelAndView("raBillReport", params);
    }

    public BigDecimal calTotal(List<BOQDetailsListDTO> dtoList) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BOQDetailsListDTO boqDetailsListDTO : dtoList) {
            totalAmount = totalAmount.add(boqDetailsListDTO.getAmount());
        }
        return totalAmount;
    }
}
