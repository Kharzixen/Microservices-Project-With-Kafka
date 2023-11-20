package com.kharzixen.ordervalidationserviceretry1.client;


import com.kharzixen.ordervalidationserviceretry1.dto.InventoryDto;
import com.kharzixen.ordervalidationserviceretry1.errorHandling.exception.ClientConnectionException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


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
    public List<InventoryDto> inventoryClientFallbackMethod(Throwable t) {
        throw new ClientConnectionException("Inventory service is down: " + t.getMessage());
    }
}
