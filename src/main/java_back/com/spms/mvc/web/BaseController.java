package com.spms.mvc.web;

import com.spms.mvc.library.helper.CurrentUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SonamPC on 12-Apr-19.
 */
public class BaseController {

    protected CurrentUser currentUser;

    protected String getReportSourcePath(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/jasperreport/spms");
        return path.replace("\\", "//");
    }

    protected String getReportOutputPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/resources/reports/");
    }

    protected CurrentUser getCurrentUser(HttpServletRequest request) {
        return (CurrentUser) request.getSession().getAttribute("currentUser");
    }

}
