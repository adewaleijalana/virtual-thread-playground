package com.javaguru.part7.security.threadlocal;

import com.javaguru.part7.security.SecurityContext;
import com.javaguru.part7.security.UserRole;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private static final String VALID_PASSWORD = "password";

    private static final Map<Integer, UserRole> USER_ROLE = Map.of(
            1, UserRole.ADMIN,
            2, UserRole.EDITOR,
            3, UserRole.VIEWER
    );

    public static void login(Integer userId, String password, Runnable runnable) {
        if (!VALID_PASSWORD.equals(password)) {
            throw new SecurityException("Invalid password");
        }

        try {
            var securityContext = new SecurityContext(userId, USER_ROLE.getOrDefault(userId, UserRole.ANONYMOUS));
            SecurityContextHolder.setSecurityContext(securityContext);
            runnable.run();
        }finally {
            SecurityContextHolder.clearSecurityContext();
        }
    }
}
