package com.javaguru.part6.externalservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class Client {
    private final static Logger log = LoggerFactory.getLogger(Client.class);
    private static final String PRODUCT_REQUEST_FORMAT = "http://localhost:7070/sec01/product/%d";
    private static final String RATING_REQUEST_FORMAT = "http://localhost:7070/sec01/rating/%d";


    public static String getProduct(int productId) {
//        log.info("Getting product with id {} and thread: {}", productId, Thread.currentThread().isVirtual());
        return callExternalService(String.format(PRODUCT_REQUEST_FORMAT, productId));
    }

    public static Integer getRating(int ratingId) {
        return Integer.parseInt(callExternalService(String.format(RATING_REQUEST_FORMAT, ratingId)));
    }


    private static String callExternalService(final String uri) {
        log.info("Calling external service " + uri);
        try(var stream = URI.create(uri).toURL().openStream()) {
            return new String(stream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
