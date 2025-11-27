package com.javaguru.part5;

import com.javaguru.part4.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.stream.Gatherers;
import java.util.stream.IntStream;

public class MapConcurrent {
    private static final Logger log = LoggerFactory.getLogger(MapConcurrent.class.getName());

    static void main() {

        var productsInfos = IntStream.range(1, 50)
                .boxed()
                .gather(Gatherers.mapConcurrent(50, MapConcurrent::getProductInfo))
                .toList();

        log.info("size = {}", productsInfos.size());

    }

    private static String getProductInfo(final int productId) {
        var product = Client.getProduct(productId);
        log.info("{} => {}", productId, product);
        return product;
    }
}
