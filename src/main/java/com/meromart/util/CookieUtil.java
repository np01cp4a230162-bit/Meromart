package com.meromart.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CookieUtil — convenient static helpers for reading and writing HTTP cookies.
 *
 * VIVA NOTE:
 * Cookies are small text files stored on the CLIENT (browser).
 * Unlike sessions (server-side), cookies persist across browser restarts when
 * given a MaxAge > 0. We use them for the "Remember Me" feature.
 */
public class CookieUtil {

    private CookieUtil() {}

    /**
     * Reads a cookie value by name. Returns null if not found.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Adds a persistent cookie to the response.
     * @param maxAgeSeconds  lifetime in seconds; use 0 to delete the cookie
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeSeconds);
        cookie.setPath("/"); // available across the entire web app
        response.addCookie(cookie);
    }

    /** Deletes a cookie by setting its MaxAge to 0. */
    public static void deleteCookie(HttpServletResponse response, String name) {
        addCookie(response, name, "", 0);
    }
}
