package com.spms.mvc.web;

import com.spms.mvc.library.helper.CurrentUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SonamPC on 12-Apr-19.
 */
public class BaseController {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvxyz0123456789";

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


    public static String generateSaltValue(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
