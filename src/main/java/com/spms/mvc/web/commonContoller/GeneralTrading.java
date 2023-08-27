package com.spms.mvc.web.commonContoller;

import com.spms.mvc.library.helper.CurrentUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class GeneralTrading {

    @ResponseBody
    @RequestMapping(value = "isGeneralTrader", method = RequestMethod.POST)
    public boolean isCurrentUserGeneralTrader(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return currentUser.getBusinessType() == 8;
    }


    @ResponseBody
    @RequestMapping(value = "getCompanyCode", method = RequestMethod.POST)
    public String getCompanyCode(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return currentUser.getLoginId();
    }

    @ResponseBody
    @RequestMapping(value = "getSiNo", method = RequestMethod.POST)
    public Integer getSiNo(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return 1;
    }


}
