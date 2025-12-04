package com.javaguru.part7.security.threadlocal;

import com.javaguru.part7.security.SecurityContext;
import com.javaguru.part7.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ThreadLocal<SecurityContext> contextHolder = ThreadLocal.withInitial(() -> ANONYMOUS_CONTEXT);

    static void setSecurityContext(SecurityContext securityContext) {
        contextHolder.set(securityContext);
    }

    static void clearSecurityContext() {
        contextHolder.remove();
    }
    public static SecurityContext getContext() {
        return contextHolder.get();
    }
}
