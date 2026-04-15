package com.meromart.controller.auth;

import com.meromart.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * LogoutServlet — invalidates the session and redirects to the login page.
 *
 * VIVA NOTE:
 * session.invalidate() destroys the session object on the server, removing all
 * stored attributes (including 'current_user'). After this, the user is effectively
 * logged out. Any subsequent request to a protected page will be caught by AuthFilter
 * and redirected back here.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUtil.invalidate(request);
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
