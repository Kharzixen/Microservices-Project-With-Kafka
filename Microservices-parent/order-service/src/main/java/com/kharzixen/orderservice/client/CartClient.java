package com.kharzixen.orderservice.client;

import com.kharzixen.orderservice.dto.CartDto;
import com.kharzixen.orderservice.error_handling.exceptions.ClientConnectionException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class CartClient {
    private final WebClient.Builder webClientBuilder;


    @CircuitBreaker(name = "cart-service", fallbackMethod = "cartClientFallbackMethod")
    public CartDto getCartById(String cartId) {
        return webClientBuilder.build().get()
                .uri("http://cart-service/api/carts/", uriBuilder -> uriBuilder.path("/{id}").build(cartId))
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    private CartDto cartClientFallbackMethod(Throwable t){
        throw new ClientConnectionException("Order service currently unavailable. (Cart service is down)");
    }
}
