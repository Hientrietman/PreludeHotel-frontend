package com.prelude.social_app.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;
@Component
public class StringFormat {
    /**
     * Generates a 6-digit random verification code.
     *
     * @return A string representing the verification code.
     */
    public static String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }

    /**
     * Generates a random username using a UUID.
     *
     * @return A string representing the generated username.
     */
    public static String generateUserName() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }
    public String cleanString(String phone) {
        return phone.replaceAll("[^0-9]", ""); // Loại bỏ tất cả các ký tự không phải số
    }
}
