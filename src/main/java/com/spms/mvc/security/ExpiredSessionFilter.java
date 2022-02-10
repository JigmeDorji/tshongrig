package com.spms.mvc.security;

/**
 * Created by SonamPC on 17-Jul-18.
 */

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExpiredSessionFilter extends GenericFilterBean {

    static final String FILTER_APPLIED = "__spring_security_expired_session_filter_applied";

    public ExpiredSessionFilter() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
            return;
        }

        request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
        if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "SESSION_TIMED_OUT");
            return;
        } else {
            response.sendRedirect("login.jsp");

        }
        chain.doFilter(request, response);
    }
}
