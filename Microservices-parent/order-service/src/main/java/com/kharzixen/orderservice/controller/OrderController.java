package com.kharzixen.orderservice.controller;

import com.kharzixen.orderservice.error_handling.error_messages.ErrorResponse;
import com.kharzixen.orderservice.error_handling.exceptions.ClientConnectionException;
import com.kharzixen.orderservice.error_handling.exceptions.InsufficientInventoryException;
import com.kharzixen.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> placeOrder(@RequestParam(name = "cartId", required = true) String cartId){
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(orderService.createOrder(cartId), HttpStatus.OK));
    }

    @ExceptionHandler(ClientConnectionException.class)
    public ResponseEntity<ErrorResponse> handleClientConnectionException(ClientConnectionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientInventoryException(InsufficientInventoryException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
