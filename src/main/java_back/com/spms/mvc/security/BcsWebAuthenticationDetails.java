package com.spms.mvc.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: BcsWebAuthenticationDetails
 * Date:  2021-Aug-01
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Aug-01
 * Change Description:
 * Search Tag:
 */
public class BcsWebAuthenticationDetails extends WebAuthenticationDetails {

    private final Integer companyId;

    public BcsWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.companyId = Integer.parseInt(request.getParameter("companyId"));
    }

    public Integer getCompanyId() {
        return companyId;
    }
}
