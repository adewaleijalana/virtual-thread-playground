package com.javaguru.part7.security;

public record SecurityContext(Integer userId, UserRole role) {
    public boolean hasPermission(UserRole userRole) {
        return this.role.ordinal() <= userRole.ordinal();
    }
}
