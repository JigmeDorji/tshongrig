package com.spms.mvc.web;

import com.spms.mvc.dto.AccSaleInvoiceGenerationDTO;
import com.spms.mvc.dto.AccSaleInvoiceGenerationListDTO;
import com.spms.mvc.dto.MoneyReceiptDTO;
import com.spms.mvc.library.helper.*;
import com.spms.mvc.service.AccSaleInvoiceGenerationService;
import com.spms.mvc.service.MoneyReceiptService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by jigme.dorji on 10/27/2020.
 */
@Controller
@RequestMapping("/moneyReceipt")
public class MoneyReceiptController extends BaseController {

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private AccSaleInvoiceGenerationService accSaleInvoiceGenerationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("moneyReceiptNo", moneyReceiptService.getMoneyReceiptNo(currentUser));
        model.addAttribute("partyList", moneyReceiptService.getPartyLedgerList(currentUser.getCompanyId()));
        model.addAttribute("mobilizationAdvPartyList", moneyReceiptService.getMobilizationPartyLedgerList(currentUser.getCompanyId()));
        model.addAttribute("materialAdvPartyList", moneyReceiptService.getMaterialAdvPartyLedgerList(currentUser.getCompanyId()));
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        model.addAttribute("currentDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        return new ModelAndView("moneyReceipt");
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, MoneyReceiptDTO moneyReceiptDTO) throws
            IOException, ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return moneyReceiptService.save(moneyReceiptDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/getReceivableAmt", method = RequestMethod.GET)
    public Double getReceivableAmt(HttpServletRequest request, Integer partyLedgerId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return moneyReceiptService.getReceivableAmt(partyLedgerId, currentUser);
    }

    @RequestMapping(value = "/generateMoneyReceipt")
    public ModelAndView generateSaleInvoice(HttpServletRequest request, ModelAndView modelAndView, String moneyReceiptNo, String partyName, Double amount, Double tDSAmount,Double retentionAmount,Double mobilizationAdvAmount,Double materialAdvAmount,String paymentMode) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        List<MoneyReceiptDTO> dtoList = moneyReceiptService.getMoneyReceiptDetails(moneyReceiptNo, currentUser.getCompanyId());
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dtoList, false);
        Map<String, Object> params = new HashMap<String, Object>();

        retentionAmount=retentionAmount==null?0.0:retentionAmount;
        mobilizationAdvAmount=mobilizationAdvAmount==null?0.0:mobilizationAdvAmount;
        materialAdvAmount=materialAdvAmount==null?0.0:materialAdvAmount;

        amount=amount-(tDSAmount+retentionAmount+mobilizationAdvAmount+materialAdvAmount);
        params.put("datasource", jrDataSource);
        params.put("moneyReceiptNo", moneyReceiptNo);
        params.put("partyName", partyName);
        params.put("amount", amount);
        params.put("amountInWords", NumberInWords.convert(amount));
        params.put("tDSAmount", tDSAmount);
        params.put("receiptDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyMailingAddress", currentUser.getCompanyAdd());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("printedDate", new Date());
        params.put("userName", currentUser.getTxtUserName());
        params.put("paymentMode", paymentMode);
        modelAndView = new ModelAndView("moneyReceiptReport", params);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLedgerUnderIncome", method = RequestMethod.GET)
    public List<DropdownDTO> getAllLedgerUnderIncome(HttpServletRequest request) {
        return moneyReceiptService.getAllLedgerUnderIncome(getCurrentUser(request));
    }
}
