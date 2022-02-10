package com.spms.mvc.web;

import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.service.LedgerGroupListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by user on 2/29/2020.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/ledgerGroupList")
public class LedgerGroupListController {
    @Autowired
    private LedgerGroupListService ledgerGroupListService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        return "ledgerGroupList";
    }

    @ResponseBody
    @RequestMapping(value = "/getLedgerGroupList", method = RequestMethod.GET)
    public List<AccProfitAndLossReportDTO> getLedgerGroupList(HttpServletRequest request, Integer accountTypeId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return ledgerGroupListService.getLedgerGroupList(currentUser, accountTypeId);
    }

}
