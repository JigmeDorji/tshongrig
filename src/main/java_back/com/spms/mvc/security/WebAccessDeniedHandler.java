/**
 * Component Name: Spare part management
 * Name: WebAccessDeniedHandler
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 * @author: bikash.rai
 * Creation: 04-May-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebAccessDeniedHandler implements AccessDeniedHandler {
    //region private variable
    private String accessDeniedUrl;
    //endregion

    //region public method
    public void setAccessDeniedUrl(String accessDeniedUrl) {
        this.accessDeniedUrl = accessDeniedUrl;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws
            IOException, ServletException {
        String url = request.getServletPath();
        response.sendRedirect(accessDeniedUrl);
    }
    //endregion
}
