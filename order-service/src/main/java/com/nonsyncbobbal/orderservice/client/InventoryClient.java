package com.nonsyncbobbal.orderservice.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
@Retry(name = "inventory")
public interface InventoryClient {
    Logger log = LoggerFactory.getLogger(InventoryClient.class);
    @RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
        // Handle the fallback logic here
        log.info("Cannot get  inventory for skuCode: {}, Error: {}", skuCode, throwable.getMessage());
        return false; // or any other default value
    }

}
