package com.spms.mvc.web;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.AutoVoucherDTO;
import com.spms.mvc.dto.VoucherDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.library.helper.*;
import com.spms.mvc.service.AutoVoucherService;
import com.spms.mvc.service.MoneyReceiptService;
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
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: PaymentController
 * Date:  2021-May-15
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-15
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/payment")
public class PaymentController extends BaseController {

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private AutoVoucherService autoVoucherService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = getCurrentUser(request);
        model.addAttribute("paymentDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        model.addAttribute("paidForTypeList", autoVoucherService.getPaidForTypeList());
        return "payment";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(HttpServletRequest request, AutoVoucherDTO autoVoucherDTO) throws
            IOException, ParseException {
        autoVoucherDTO.setTypeId(1);
        return autoVoucherService.save(autoVoucherDTO, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getLedgerUnderPayableForRepayment", method = RequestMethod.GET)
    public List<DropdownDTO> getLedgerUnderPayableForRepayment(HttpServletRequest request) {
        return autoVoucherService.getLedgerUnderPayableForRepayment(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLPayableLedgerExcludingTds", method = RequestMethod.GET)
    public List<DropdownDTO> getAllLPayableLedgerExcludingTds(HttpServletRequest request) {
        return autoVoucherService.getAllLPayableLedgerExcludingTds(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLedgerUnderExpenseForCost", method = RequestMethod.GET)
    public List<DropdownDTO> getAllLedgerUnderExpenseForCost(HttpServletRequest request) {
        return autoVoucherService.getAllLedgerUnderExpenseForCost(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/fetchTDSPayableList", method = RequestMethod.GET)
    public ResponseMessage fetchTDSPayableList(HttpServletRequest request) {
        return autoVoucherService.fetchTDSPayableList(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLedgerUnderAdvancePaid", method = RequestMethod.GET)
    public List<DropdownDTO> getAllLedgerUnderAdvancePaid(HttpServletRequest request) {
        return autoVoucherService.getAllLedgerUnderAccountType(getCurrentUser(request),
                AccountTypeEnum.PARTY_ADVANCE_PAID.getValue());
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLedgerUnderAdvanceReceived", method = RequestMethod.GET)
    public List<AutoVoucherDTO> getAllLedgerUnderAdvanceReceived(HttpServletRequest request) {
        return autoVoucherService.getAllLedgerUnderAdvanceReceived(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getRepaymentAmount", method = RequestMethod.GET)
    public Double getRepaymentAmount(HttpServletRequest request, String ledgerId) {
        return autoVoucherService.getAmountByLedgerId(getCurrentUser(request), ledgerId);
    }

    @ResponseBody
    @RequestMapping(value = "/getCostTypeByLedgerId", method = RequestMethod.GET)
    public Integer getCostTypeByLedgerId(HttpServletRequest request, String ledgerId) {
        return autoVoucherService.getCostTypeByLedgerId(getCurrentUser(request), ledgerId);
    }

    @RequestMapping(value = "/generateReport")
    public ModelAndView generateReport(HttpServletRequest request, Integer voucherNo) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");

        List<VoucherDetailDTO> voucherDetailDTOList = autoVoucherService.getVoucherDetail(voucherNo,currentUser);

        JRDataSource jrDataSource = new JRBeanCollectionDataSource(voucherDetailDTOList, false);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("datasource", jrDataSource);
        params.put("totalBillAmtInWords", NumberInWords.convert(calTotal(voucherDetailDTOList).longValue()));
        params.put("receiptDate", new Date());
        params.put("companyName", currentUser.getCompanyName());
        params.put("companyContact", currentUser.getContact());
        params.put("companyEmailID", currentUser.getEmail());
        params.put("mailingAddress", currentUser.getMailingAddress());
        params.put("printedDate", new Date());
        params.put("userName", currentUser.getTxtUserName());
        return new ModelAndView("raBillReport", params);
    }

    public Double calTotal(List<VoucherDetailDTO> dtoList) {
        Double totalAmount = 0.0;
        for (VoucherDetailDTO voucherDetailDTO : dtoList) {
            totalAmount = totalAmount + (voucherDetailDTO.getDrcrAmount());
        }
        return totalAmount;
    }

}
