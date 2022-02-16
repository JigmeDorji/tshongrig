package com.spms.mvc.web;

import com.spms.mvc.dto.AutoVoucherDTO;
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
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: BankTransferController
 * Date:  2022-Jan-23
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-23
 * Change Description:
 * Search Tag:
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/bankTransfer")
public class BankTransferController extends BaseController {

    @Autowired
    private MoneyReceiptService moneyReceiptService;

    @Autowired
    private AutoVoucherService autoVoucherService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("paymentDate", DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY));
        List<DropdownDTO> bankList = moneyReceiptService.getBankList(getCurrentUser(request).getCompanyId());
        model.addAttribute("bankList", bankList == null ? new ArrayList<DropdownDTO>() : bankList);
        return "bankTransfer";
    }

    @ResponseBody
    @RequestMapping(value = "/bankTransferDetail", method = RequestMethod.POST)
    public ResponseMessage bankTransferDetail(HttpServletRequest request, AutoVoucherDTO autoVoucherDTO) throws
            IOException, ParseException {
        autoVoucherDTO.setTypeId(1);
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return autoVoucherService.bankTransferDetail(autoVoucherDTO, currentUser);
    }
}
