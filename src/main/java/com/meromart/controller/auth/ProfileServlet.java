package com.meromart.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * ProfileServlet — serves the user profile / dashboard page.
 *
 * VIVA NOTE:
 * The AuthFilter guarantees that only authenticated users reach this servlet.
 * We simply forward to the profile JSP; the current_user is already in the session
 * and accessible via EL: ${sessionScope.current_user.name}
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/auth/profile.jsp").forward(request, response);
    }
}
