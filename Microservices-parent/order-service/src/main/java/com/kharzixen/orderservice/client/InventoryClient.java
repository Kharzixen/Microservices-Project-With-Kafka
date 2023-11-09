package com.kharzixen.orderservice.client;

import com.kharzixen.orderservice.Dto.CartDto;
import com.kharzixen.orderservice.Dto.InventoryDto;
import com.kharzixen.orderservice.error_handling.exceptions.ClientConnectionException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
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
    private List<InventoryDto> inventoryClientFallbackMethod(Throwable t){
        throw new ClientConnectionException("Order service currently unavailable. (Inventory service is down)");
    }
}
