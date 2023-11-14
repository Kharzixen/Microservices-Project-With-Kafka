package com.kharzixen.ordervalidationservice.client;


import com.kharzixen.ordervalidationservice.dto.InventoryDto;
import com.kharzixen.ordervalidationservice.error_handling.exception.ClientConnectionException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient.Builder webClientBuilder;

    @CircuitBreaker(name = "inventory-service", fallbackMethod = "inventoryClientFallbackMethod")
    public List<InventoryDto> getInventoryByProductId(List<String> productIds) {
        return webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("productIds", productIds).build())
                .retrieve()
                .bodyToFlux(InventoryDto.class).collectList().block();
    }

    //fallback logic prototype
    public List<InventoryDto> inventoryClientFallbackMethod(Throwable t) {
        log.info("fallback logic fired");
        //throw new ClientConnectionException("Order service currently unavailable. (Inventory service is down)");
        throw new ClientConnectionException("Inventory service is down)");
    }
}
