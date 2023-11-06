package com.kharzixen.orderservice.client;

import com.kharzixen.orderservice.Dto.CartDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartServiceClient {
    @GetMapping("/api/carts/{cartId}")
    CartDto getCartById(@PathVariable("cartId") String cartId);
}
