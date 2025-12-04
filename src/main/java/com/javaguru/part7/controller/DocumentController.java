package com.javaguru.part7.controller;

import com.javaguru.part7.security.SecurityContext;
import com.javaguru.part7.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class DocumentController {
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    private final Supplier<SecurityContext> securityContextSupplier;

    public DocumentController(Supplier<SecurityContext> securityContextSupplier) {
        this.securityContextSupplier = securityContextSupplier;
    }

    public void read(){
        this.validateUserRole(UserRole.VIEWER);
        log.info("reading document");
    }

    public void edit(){
        this.validateUserRole(UserRole.EDITOR);
        log.info("editing document");
    }

    public void delete(){
        this.validateUserRole(UserRole.ADMIN);
        log.info("deleting document");
    }

    private void validateUserRole(UserRole role) {
        SecurityContext securityContext = securityContextSupplier.get();
        if(!securityContext.hasPermission(role)){
            log.error("User {} doesn't have {} permission to perform this operation", securityContext.userId(), role);
            throw new SecurityException("User doesn't have permission: %s to perform this operation".formatted(role));
        }
    }
}
