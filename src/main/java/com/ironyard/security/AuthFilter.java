package com.ironyard.security;

import com.ironyard.data.CUser;
import com.ironyard.repo.CUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by matthewhug on 3/22/17.
 */
@Component
@WebFilter(filterName = "RestFilter")
public class AuthFilter implements Filter{

    @Autowired
    CUserRepo cUserRepo;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        TokenMaster tm = new TokenMaster();

        String authToken = req.getHeader("x-authorization-key");
        CUser cu = null;
        if(authToken != null && !authToken.equals("null")) {
            cu = cUserRepo.findOne(tm.getUserIdFromToken(authToken));
        }
        boolean authCheck = (authToken != null && !authToken.equals("null"));
        String reqUri = req.getRequestURI();
        if((authToken != null && !authToken.equals("null")) || req.getRequestURI().endsWith("login.html") || req.getRequestURI().endsWith("/rest/user/login"))
        {
            filterChain.doFilter(servletRequest, servletResponse);
        } else{
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        }
    }

    @Override
    public void destroy() {

    }
}
