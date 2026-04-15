package com.meromart.util;

import com.meromart.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * SessionUtil — helper methods for reading and writing HttpSession attributes.
 *
 * VIVA NOTE:
 * An HttpSession lives on the SERVER and is identified by a cookie named JSESSIONID.
 * It persists across multiple HTTP requests from the same browser, allowing us to
 * "remember" that a user is logged in without re-authenticating on every page.
 */
public class SessionUtil {

    /** Session key under which the logged-in user is stored. */
    public static final String CURRENT_USER = "current_user";

    private SessionUtil() {}

    /** Stores the logged-in user in the session. */
    public static void setCurrentUser(HttpServletRequest request, UserModel user) {
        HttpSession session = request.getSession(true); // create if absent
        session.setAttribute(CURRENT_USER, user);
    }

    /**
     * Retrieves the logged-in user from the session.
     * @return UserModel or null if not logged in.
     */
    public static UserModel getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // do NOT create a new session
        if (session == null) return null;
        return (UserModel) session.getAttribute(CURRENT_USER);
    }

    /** Returns true when a valid session with a user object exists. */
    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    /** Returns true when the logged-in user has the 'admin' role. */
    public static boolean isAdmin(HttpServletRequest request) {
        UserModel user = getCurrentUser(request);
        return user != null && user.isAdmin();
    }

    /** Invalidates the session entirely, logging the user out. */
    public static void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
    }
}
