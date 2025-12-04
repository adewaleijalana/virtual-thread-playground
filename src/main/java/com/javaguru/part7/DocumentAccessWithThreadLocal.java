package com.javaguru.part7;

import com.javaguru.part7.controller.DocumentController;
import com.javaguru.part7.security.threadlocal.AuthenticationService;
import com.javaguru.part7.security.threadlocal.SecurityContextHolder;
import com.javaguru.util.CommonUtils;

import java.time.Duration;

public class DocumentAccessWithThreadLocal {

    private static DocumentController documentController = new DocumentController(SecurityContextHolder::getContext);

    static void main() {
        Thread.ofVirtual().name("admin").start(() -> accessDocument(1, "password"));
        Thread.ofVirtual().name("editor").start(() -> accessDocument(2, "password"));

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void accessDocument(Integer userId, String password){
        AuthenticationService.login(userId, password, () -> {
            documentController.read();
            documentController.edit();
            documentController.delete();
        });
    }
}
