package com.javaguru.part6.aggregator;

import com.javaguru.part4.externalservice.Client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class CompletableFutureAggregatorService {
    private final ExecutorService executorService;

    public CompletableFutureAggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDTO getProduct(final int productId)  {
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(productId), executorService)
                .exceptionally(throwable -> null);
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(productId), executorService)
                .exceptionally(throwable -> -1);
        return new ProductDTO(productId, product.join(), rating.join());
    }
}
