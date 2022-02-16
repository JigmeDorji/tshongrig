package com.spms.mvc.web;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.AutoVoucherDTO;
import com.spms.mvc.dto.LoanDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.AutoVoucherService;
import com.spms.mvc.service.MoneyReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Description: ReceiptController
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
@RequestMapping(value = "/receipt")
public class ReceiptController extends BaseController {

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private AutoVoucherService autoVoucherService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("userId", currentUser.getLoginId());
        model.addAttribute("paymentDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        model.addAttribute("bankList", moneyReceiptService.getBankList(currentUser.getCompanyId()));
        return "receipt";
    }

    @ResponseBody
    @RequestMapping(value = "/saveReceiptVoucherDetails", method = RequestMethod.POST)
    public ResponseMessage saveReceiptVoucherDetails(HttpServletRequest request, AutoVoucherDTO autoVoucherDTO) throws
            ParseException {
        autoVoucherDTO.setTypeId(3);
        return autoVoucherService.save(autoVoucherDTO, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/saveLoanDetail", method = RequestMethod.POST)
    public ResponseMessage saveLoanDetail(HttpServletRequest request, LoanDTO loanDTO) {
        return autoVoucherService.saveLoanDetail(loanDTO, getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLedgerUnderAdvanceReceived", method = RequestMethod.GET)
    public List<AutoVoucherDTO> getAllLedgerUnderAdvanceReceived(HttpServletRequest request) {
        return autoVoucherService.getAllLedgerUnderAdvanceReceived(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getLoanLedgerList", method = RequestMethod.GET)
    public List<AutoVoucherDTO> getLoanLedgerList(HttpServletRequest request) {
        return autoVoucherService.getLoanLedgerList(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getLoanDetails", method = RequestMethod.GET)
    public List<LoanDTO> getLoanDetails(HttpServletRequest request) {
        return autoVoucherService.getLoanDetails(getCurrentUser(request));
    }

    @ResponseBody
    @RequestMapping(value = "/getAllLedgerUnderAccountType", method = RequestMethod.GET)
    public List<DropdownDTO> getAllLedgerUnderAccountType(HttpServletRequest request, Integer selectionType) {
        return autoVoucherService.getAllLedgerUnderAccountType(getCurrentUser(request),
                selectionType == 4 ? AccountTypeEnum.MATERIAL_ADV.getValue() : AccountTypeEnum.MOBILIZATION_ADV.getValue());
    }

}
