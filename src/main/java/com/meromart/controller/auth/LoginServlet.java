package com.meromart.controller.auth;

import com.meromart.dao.UserDAO;
import com.meromart.model.UserModel;
import com.meromart.util.CookieUtil;
import com.meromart.util.PasswordUtil;
import com.meromart.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * LoginServlet — handles GET /login (show form) and POST /login (process credentials).
 *
 * VIVA NOTE: MVC flow for login
 *  Browser ──GET──▶ LoginServlet.doGet  ──forward──▶ pages/auth/login.jsp  (View)
 *  Browser ──POST─▶ LoginServlet.doPost ──▶ DB lookup ──▶ session ──redirect──▶ /profile
 *
 * Cookie vs Session:
 *  Cookie  = client-side storage (browser). Used here for "Remember Me" (saves email for 30 days).
 *  Session = server-side storage (Tomcat). Stores the UserModel object for the logged-in user.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();

    /** GET /login — display the login form, pre-filling email from cookie if present. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // If already logged in, skip the login page
        if (SessionUtil.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        // Read the "Remember Me" cookie and expose it to the JSP via a request attribute
        String savedEmail = CookieUtil.getCookieValue(request, "user_email");
        if (savedEmail == null) savedEmail = "";
        request.setAttribute("savedEmail", savedEmail);

        request.getRequestDispatcher("/pages/auth/login.jsp").forward(request, response);
    }

    /** POST /login — validate credentials, create session, redirect on success. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email      = request.getParameter("email");
        String password   = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Look up the user in the database
        UserModel user = userDAO.getUserByEmail(email);

        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            // ── Login Successful ───────────────────────────────────────────────
            SessionUtil.setCurrentUser(request, user);

            // Handle "Remember Me" cookie
            if ("on".equals(rememberMe)) {
                CookieUtil.addCookie(response, "user_email", email, 60 * 60 * 24 * 30); // 30 days
            } else {
                CookieUtil.deleteCookie(response, "user_email");
            }

            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            // ── Login Failed ───────────────────────────────────────────────────
            request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
            request.setAttribute("savedEmail", email != null ? email : "");
            request.getRequestDispatcher("/pages/auth/login.jsp").forward(request, response);
        }
    }
}
