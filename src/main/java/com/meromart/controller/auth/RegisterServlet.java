package com.meromart.controller.auth;

import com.meromart.dao.UserDAO;
import com.meromart.model.UserModel;
import com.meromart.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * RegisterServlet — handles GET /register (show form) and POST /register (create account).
 *
 * VIVA NOTE: Three-step validation before inserting into the DB:
 *  1. Passwords match
 *  2. Email is not already taken
 *  3. Phone number is not already taken
 * Only after all three pass do we hash the password and call the DAO.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserDAO userDAO = new UserDAO();

    /** GET /register — just show the empty registration form. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/auth/register.jsp").forward(request, response);
    }

    /** POST /register — validate input, hash password, persist to DB. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name            = request.getParameter("name");
        String email           = request.getParameter("email");
        String phone           = request.getParameter("phone");
        String password        = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // ── Validation ────────────────────────────────────────────────────────
        if (!password.equals(confirmPassword)) {
            forwardWithError(request, response, "Passwords do not match.");
            return;
        }
        if (userDAO.isEmailExists(email)) {
            forwardWithError(request, response, "This email is already registered. Please log in.");
            return;
        }
        if (userDAO.isPhoneExists(phone)) {
            forwardWithError(request, response, "This phone number is already used by another account.");
            return;
        }

        // ── Persist ───────────────────────────────────────────────────────────
        UserModel newUser = new UserModel(
            name, email, PasswordUtil.hashPassword(password), phone, "user"
        );

        boolean success = userDAO.registerUser(newUser);

        if (success) {
            request.setAttribute("successMessage", "Account created successfully! Please sign in.");
            request.getRequestDispatcher("/pages/auth/login.jsp").forward(request, response);
        } else {
            forwardWithError(request, response, "Registration failed due to a server error. Please try again.");
        }
    }

    private void forwardWithError(HttpServletRequest req, HttpServletResponse res, String message)
            throws ServletException, IOException {
        req.setAttribute("errorMessage", message);
        req.getRequestDispatcher("/pages/auth/register.jsp").forward(req, res);
    }
}
