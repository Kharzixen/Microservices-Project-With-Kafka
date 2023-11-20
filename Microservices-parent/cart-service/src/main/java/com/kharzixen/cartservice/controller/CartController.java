package com.kharzixen.cartservice.controller;

import com.kharzixen.cartservice.dto.request.CartDtoIn;
import com.kharzixen.cartservice.dto.response.CartDtoOut;
import com.kharzixen.cartservice.error_handling.errors.ErrorResponse;
import com.kharzixen.cartservice.error_handling.exceptions.CartAlreadyExistsException;
import com.kharzixen.cartservice.error_handling.exceptions.CartNotFoundException;
import com.kharzixen.cartservice.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
@Log
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDtoOut> createCart(@RequestBody CartDtoIn cartDtoIn){
        return new ResponseEntity<>(cartService.createCart(cartDtoIn), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCarts(@RequestParam(name = "userId", required = false) String userId){
        if(userId == null) {
            return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cartService.getCartByUser(userId), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDtoOut> getCartById(@PathVariable("id") String cartId){
        return new ResponseEntity<>(cartService.getCartById(cartId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartById(@PathVariable("id") String cartId){
        cartService.deleteCart(cartId);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCartAlreadyExistsException(CartAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
