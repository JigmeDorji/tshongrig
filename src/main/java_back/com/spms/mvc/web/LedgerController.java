package com.spms.mvc.web;

import com.spms.mvc.dto.LedgerDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.ResponseMessage;
import com.spms.mvc.service.LedgerService;
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
 * Created by jigmePc on 5/4/2019.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/ledger")
public class LedgerController {
    @Autowired
    LedgerService ledgerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("accTypeList", ledgerService.getAccTypeList());
        return "ledger";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseMessage save(LedgerDTO ledgerDTO, HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerService.save(ledgerDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/updateLedgerDetails", method = RequestMethod.POST)
    public ResponseMessage updateLedgerDetails(LedgerDTO ledgerDTO, HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerService.updateLedgerDetails(ledgerDTO, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteLedgerDetails", method = RequestMethod.POST)
    public ResponseMessage deleteLedgerDetails(HttpServletRequest request, Integer ledgerId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerService.deleteLedgerDetails(ledgerId, currentUser);
    }

    @ResponseBody
    @RequestMapping(value = "/isLedgerUsed", method = RequestMethod.POST)
    public ResponseMessage isLedgerUsed(HttpServletRequest request, Integer ledgerId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerService.isLedgerUsed(ledgerId, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/isBankAccType", method = RequestMethod.GET)
    public Boolean isBankAccType(Integer accTypeId) throws
            IOException {
        return ledgerService.isBankAccType(accTypeId);
    }


    @ResponseBody
    @RequestMapping(value = "/isLedgerNameExists", method = RequestMethod.GET)
    public ResponseMessage isLedgerNameExists(String ledgerName, HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerService.isLedgerNameExists(ledgerName, currentUser.getCompanyId());
    }

    @ResponseBody
    @RequestMapping(value = "/getLedgerDetails", method = RequestMethod.GET)
    public LedgerDTO getLedgerDetails(Integer ledgerId) throws
            IOException {
        return ledgerService.getLedgerDetails(ledgerId);
    }

    @ResponseBody
    @RequestMapping(value = "/getLedgerList", method = RequestMethod.GET)
    public List<LedgerDTO> getLedgerList(HttpServletRequest request) throws
            IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerService.getLedgerList(currentUser.getCompanyId());
    }
}
