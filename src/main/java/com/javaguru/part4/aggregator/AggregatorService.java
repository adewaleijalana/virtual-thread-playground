package com.javaguru.part4.aggregator;

import com.javaguru.part4.externalservice.Client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AggregatorService {
    private final ExecutorService executorService;

    public AggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDTO getProduct(final int productId)  {
        var product = executorService.submit(() -> Client.getProduct(productId));
        var rating = executorService.submit(() -> Client.getRating(productId));
        try {
            return new ProductDTO(productId, product.get(), rating.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
