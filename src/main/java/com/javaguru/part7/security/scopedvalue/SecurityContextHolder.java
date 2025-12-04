package com.javaguru.part7.security.scopedvalue;

import com.javaguru.part7.security.SecurityContext;
import com.javaguru.part7.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ScopedValue<SecurityContext> SCOPED_VALUE = ScopedValue.newInstance();

    static ScopedValue<SecurityContext> getScopedContext() {
        return SCOPED_VALUE;
    }
    public static SecurityContext getContext() {
        return SCOPED_VALUE.orElse(ANONYMOUS_CONTEXT);
    }
}
