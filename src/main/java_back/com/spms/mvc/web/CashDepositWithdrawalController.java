package com.spms.mvc.web;

import com.spms.mvc.dto.AutoVoucherDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
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
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Description: CashDepositWithdrawalController
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
@RequestMapping(value = "/cashDepositWithdrawal")
public class CashDepositWithdrawalController {

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
        return "cashDepositWithdrawal";
    }

    @ResponseBody
    @RequestMapping(value = "/saveCashDepositWithVoucherDetails", method = RequestMethod.POST)
    public ResponseMessage saveCashDepositWithVoucherDetails(HttpServletRequest request, AutoVoucherDTO autoVoucherDTO) throws
            IOException, ParseException {
        autoVoucherDTO.setTypeId(2);
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return autoVoucherService.save(autoVoucherDTO, currentUser);
    }

}
