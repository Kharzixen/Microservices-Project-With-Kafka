package com.kharzixen.orderservice.controller;

import com.kharzixen.orderservice.service.OrderService;
import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name="circuit-breaker", fallbackMethod = "placeOrderFallbackMethod")
    @TimeLimiter(name="time-limiter")
    @Retry(name = "my-retry")
    public CompletableFuture<ResponseEntity<?>> placeOrder(@RequestParam(name = "cartId", required = true) String cartId){
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(orderService.createOrder(cartId), HttpStatus.OK));
    }

    public CompletableFuture<ResponseEntity<?>> placeOrderFallbackMethod(RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> new ResponseEntity<>("Oops! Something went wrong, please try again later!\n" + runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
