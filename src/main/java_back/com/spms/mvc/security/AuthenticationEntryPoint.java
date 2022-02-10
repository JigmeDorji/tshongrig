/**
 * Component Name: Spare part management
 * Name: AuthenticationEntryPoint
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

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    //region private variable 
    private static final RequestMatcher REQUEST_MATCHER = new ELRequestMatcher("hasHeader('X-Requested-With','XMLHttpRequest')");
    //endregion

    //region public method
    public AuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        if (REQUEST_MATCHER.matches(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "SESSION_TIMED_OUT");
        } else {
            request.setAttribute("targetUrl", request.getRequestURL());
            super.commence(request, response, authException);
        }
    }
    //endregion
}
