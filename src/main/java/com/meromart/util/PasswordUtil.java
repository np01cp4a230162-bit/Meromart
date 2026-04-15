package com.meromart.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordUtil — one-way SHA-256 password hashing.
 *
 * VIVA NOTE:
 * Passwords MUST NEVER be stored in plain text.
 * SHA-256 is a cryptographic hash function: it is deterministic (same input → same output)
 * but irreversible (you cannot get the original password back from the hash).
 * To verify a login: hash the submitted password and compare it with the stored hash.
 */
public class PasswordUtil {

    private PasswordUtil() {}

    /**
     * Hashes a raw password using SHA-256.
     * @param rawPassword the plain-text password from the form
     * @return 64-character hexadecimal hash string
     */
    public static String hashPassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is always available in the JVM — this should never happen
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verifies a raw password against a stored hash.
     * @param rawPassword   the plain-text password to check
     * @param storedHash    the hash retrieved from the database
     * @return true if the passwords match
     */
    public static boolean verifyPassword(String rawPassword, String storedHash) {
        return hashPassword(rawPassword).equals(storedHash);
    }
}
