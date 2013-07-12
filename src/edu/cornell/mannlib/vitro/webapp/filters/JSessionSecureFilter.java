/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSessionSecureFilter implements Filter {
    private FilterConfig filterConfig = null;

    private static final Log log = LogFactory.getLog(JSessionSecureFilter.class.getName());

    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Securing JSESSIONID.");
        this.filterConfig = filterConfig;
    }

    public void doFilter(   ServletRequest request,
                            ServletResponse response,
                            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        String sessionid = httpServletRequest.getSession().getId();
        httpServletResponse.setHeader("SET-COOKIE", "JSESSIONID=" + sessionid + "; Path=/; HttpOnly; Secure");
        

        filterChain.doFilter(request, response);
    }

    public void destroy() {
        filterConfig = null;
    }
}
