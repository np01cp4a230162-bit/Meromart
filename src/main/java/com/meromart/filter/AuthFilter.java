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
 * AuthFilter — intercepts every request and enforces authentication.
 *
 * VIVA NOTE:
 * A Filter is an object that pre-processes (and optionally post-processes) HTTP requests
 * BEFORE they reach the Servlet or JSP. Instead of duplicating "is the user logged in?"
 * checks in every Controller, we declare it ONCE here (DRY principle).
 *
 * Lifecycle:
 *  1. init()      — called once at startup
 *  2. doFilter()  — called for every matching request
 *  3. destroy()   — called at shutdown
 *
 * Authentication vs. Authorization:
 *  - Authentication: "Who are you?" → is there a valid session with a user object?
 *  - Authorization:  "What can you do?" → does the user have the 'admin' role for /admin/* pages?
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialisation needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req = (HttpServletRequest)  request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // ── Public resources — always let through ──────────────────────────────
        // Prevents an infinite redirect loop: /login → filter → redirect to /login → ...
        if (isPublicResource(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // ── Authentication check ───────────────────────────────────────────────
        UserModel currentUser = SessionUtil.getCurrentUser(req);

        if (currentUser == null) {
            // Not logged in — redirect to the login servlet
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ── Authorization: protect /admin/* from regular users ─────────────────
        if (uri.startsWith(req.getContextPath() + "/admin")
                || uri.contains("/pages/admin/")) {
            if (!currentUser.isAdmin()) {
                res.sendRedirect(req.getContextPath() + "/pages/common/access-denied.jsp");
                return;
            }
        }

        // ── No-cache headers for all authenticated pages ───────────────────────
        // Prevents the browser from caching protected pages after logout.
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma",        "no-cache");
        res.setDateHeader("Expires",   0);

        // All checks passed — continue to the requested resource
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nothing to clean up
    }

    /**
     * Returns true for URLs that should always be accessible without a session.
     */
    private boolean isPublicResource(String uri) {
        return uri.endsWith("/login")
            || uri.endsWith("/register")
            || uri.endsWith("login.jsp")
            || uri.endsWith("register.jsp")
            || uri.contains("/assets/")
            || uri.contains("/pages/auth/login.jsp")
            || uri.contains("/pages/auth/register.jsp")
            || uri.contains("/pages/common/access-denied.jsp")
            || uri.contains("/pages/common/error.jsp");
    }
}
