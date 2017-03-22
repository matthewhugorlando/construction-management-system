package com.ironyard.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by matthewhug on 3/22/17.
 */
@WebFilter(filterName = "RestFilter")
public class AuthFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        System.out.println("Auth Filter called");

        String authToken = req.getHeader("x-authorization-key");
        System.out.println(authToken);
        System.out.println(req.getRequestURI().endsWith("login.html"));
        if(authToken == null && !req.getRequestURI().endsWith("login.html"))
        {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
