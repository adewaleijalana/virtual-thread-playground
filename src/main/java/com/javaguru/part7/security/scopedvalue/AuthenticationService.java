package com.javaguru.part7.security.scopedvalue;

import com.javaguru.part7.security.SecurityContext;
import com.javaguru.part7.security.UserRole;

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

        var securityContext = new SecurityContext(userId, USER_ROLE.getOrDefault(userId, UserRole.ANONYMOUS));
        ScopedValue.where(SecurityContextHolder.getScopedContext(), securityContext)
                .run(runnable);
    }

    public static void runAsAdmin(Runnable runnable) {
        var context = SecurityContextHolder.getContext();
        var securityContext = new SecurityContext(context.userId(), UserRole.ADMIN);
        ScopedValue.where(SecurityContextHolder.getScopedContext(), securityContext)
                .run(runnable);
    }
}
