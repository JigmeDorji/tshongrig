package com.spms.mvc.web;

import com.spms.mvc.dto.AccSaleInvoiceGenerationDTO;
import com.spms.mvc.dto.AccSaleInvoiceGenerationListDTO;
import com.spms.mvc.dto.SaleRecordDTO;
import com.spms.mvc.dto.ServiceChargesDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.NumberInWords;
import com.spms.mvc.library.helper.ResponseMessage;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bcass Sawa on 10/24/2019.
 */

@Controller
@RequestMapping("/saleInvoiceGeneration")
public class AccSaleInvoiceGenerationController {

    @Autowired
    private AccSaleInvoiceGenerationService accSaleInvoiceGenerationService;

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list(Model model, HttpServletRequest request) {
        model.addAttribute("saleInvoiceNo", accSaleInvoiceGenerationService
                .getMaxCountOfInvoiceNo((CurrentUser) request.getSession()
                        .getAttribute("currentUser")));
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("invoiceDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        return new ModelAndView("accSaleInvoiceGeneration");
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, AccSaleInvoiceGenerationDTO accSaleInvoiceGenerationDTO) throws
            IOException, ParseException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return accSaleInvoiceGenerationService.save(accSaleInvoiceGenerationDTO, currentUser);
    }


    @ResponseBody
    @RequestMapping(value = "/getLedgerList", method = RequestMethod.GET)
    public List<AccSaleInvoiceGenerationListDTO> getLedgerList(HttpServletRequest request) {
        return accSaleInvoiceGenerationService.getLedgerList((CurrentUser) request.getSession().getAttribute("currentUser"));
    }

    @ResponseBody
    @RequestMapping(value = "/getPartyList", method = RequestMethod.GET)
    public List<AccSaleInvoiceGenerationDTO> getPartyList(HttpServletRequest request) {
        return accSaleInvoiceGenerationService.getPartyList((CurrentUser) request.getSession().getAttribute("currentUser"));
    }

    @ResponseBody
    @RequestMapping(value = "/getPartyDetail", method = RequestMethod.GET)
    public AccSaleInvoiceGenerationDTO getPartyDetail(HttpServletRequest request, String partyName) {
        return accSaleInvoiceGenerationService.getPartyDetail((CurrentUser) request.getSession().getAttribute("currentUser"), partyName);
    }


    @RequestMapping(value = "/generateSaleInvoice")
    public ModelAndView generateSaleInvoice(HttpServletRequest request, ModelAndView modelAndView, Integer saleInvoiceId, String partyName) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        AccSaleInvoiceGenerationDTO accSaleInvoiceGenerationDTO = accSaleInvoiceGenerationService.getInvoiceDetails(saleInvoiceId, currentUser.getCompanyId());
        List<AccSaleInvoiceGenerationListDTO> dtoList = accSaleInvoiceGenerationService.getInvoiceDetailsList(saleInvoiceId, currentUser.getCompanyId());
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dtoList, false);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("datasource", jrDataSource);
        params.put("partyName", accSaleInvoiceGenerationDTO.getPartyName());
        params.put("partyAddress", accSaleInvoiceGenerationDTO.getPartyAddress());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyMailingAddress", currentUser.getCompanyAdd());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("invoiceNo", accSaleInvoiceGenerationDTO.getInvoiceNo());
        params.put("invoiceDate", accSaleInvoiceGenerationDTO.getInvoiceDate());
        params.put("totalBillAmtInWords", NumberInWords.convert(accSaleInvoiceGenerationDTO.getAmount().longValue()));
        params.put("printedDate", new Date());
        params.put("userName", currentUser.getTxtUserName());
        modelAndView = new ModelAndView("saleInvoiceReport", params);
        return modelAndView;
    }


}
