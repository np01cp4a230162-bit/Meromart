package com.meromart.filter;

import com.meromart.model.UserModel;
import com.meromart.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * AdminFilter — restricts all /admin/* URLs to users with the 'admin' role.
 *
 * VIVA NOTE:
 * This filter runs AFTER AuthFilter (which already verified that the user is logged in).
 * AdminFilter provides the second layer of security: Authorization.
 * Authentication = "Who are you?"  |  Authorization = "What are you allowed to do?"
 */
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req = (HttpServletRequest)  request;
        HttpServletResponse res = (HttpServletResponse) response;

        UserModel currentUser = SessionUtil.getCurrentUser(req);

        if (currentUser == null || !currentUser.isAdmin()) {
            // Return HTTP 403 if a non-admin tries to access this URL
            res.sendRedirect(req.getContextPath() + "/pages/common/access-denied.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
