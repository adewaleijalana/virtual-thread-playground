package com.javaguru.part4;

import com.javaguru.part4.aggregator.AggregatorService;
import com.javaguru.part4.aggregator.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.IntStream;

public class AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(AggregatorDemo.class);

    static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            var aggregator = new AggregatorService(executorService);
//            final ProductDTO product = aggregator.getProduct(1);
//            log.info("product {}", product);

            var futures = IntStream.range(1, 50)
                    .mapToObj(id -> executorService.submit(() -> aggregator.getProduct(id)))
                    .toList();

            final List<ProductDTO> productDTOList = futures.stream().map(AggregatorDemo::getProduct)
                    .toList();

            log.info("{} products found", productDTOList);
        }

    }

    private static ProductDTO getProduct(final Future<ProductDTO> productDTOFuture) {
        try {
            return productDTOFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static <R, E extends Exception> R process(ThrowableTask<R, E> task) throws E {
       try {
           return task.execute();
       }catch (Exception e) {
           throwActualException(e);
           return null;
       }
    }

    interface ThrowableTask<R, E extends Exception> {
        R execute() throws E;
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> void throwActualException(Exception exception) throws E {
        throw (E) exception;
    }
}
