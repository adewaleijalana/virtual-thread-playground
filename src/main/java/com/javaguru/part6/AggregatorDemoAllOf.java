package com.javaguru.part6;

import com.javaguru.part4.aggregator.AggregatorService;
import com.javaguru.part4.aggregator.ProductDTO;
import com.javaguru.part6.aggregator.CompletableFutureAggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class AggregatorDemoAllOf {
    private static final Logger log = LoggerFactory.getLogger(AggregatorDemoAllOf.class);

    static void main(String[] args) {
        try(var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            var aggregator = new CompletableFutureAggregatorService(executorService);

            var futures = IntStream.range(1, 100)
                    .mapToObj(id -> CompletableFuture.supplyAsync(() -> aggregator.getProduct(id), executorService))
                    .toList();

            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

            final var productDTOList = futures.stream().map(CompletableFuture::join)
                    .toList();

            log.info("products found: {}", productDTOList);
        }

    }

    private static ProductDTO getProduct(final Future<ProductDTO> productDTOFuture) {
        try {
            return productDTOFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
