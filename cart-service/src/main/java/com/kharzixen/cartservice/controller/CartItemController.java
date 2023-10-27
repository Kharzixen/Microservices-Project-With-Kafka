package com.kharzixen.cartservice.controller;

import com.kharzixen.cartservice.dto.request.ItemDtoIn;
import com.kharzixen.cartservice.dto.response.ItemDtoOutDetailed;
import com.kharzixen.cartservice.error_handling.errors.ErrorResponse;
import com.kharzixen.cartservice.error_handling.exceptions.CartNotFoundException;
import com.kharzixen.cartservice.error_handling.exceptions.ItemInCartException;
import com.kharzixen.cartservice.error_handling.exceptions.ItemNotFoundException;
import com.kharzixen.cartservice.service.ItemService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/carts")
public class CartItemController {

    private final ItemService itemService;
    @PostMapping("/{id}/items")
    public ItemDtoOutDetailed createItemInCart(@PathVariable("id") String cartId, @RequestBody ItemDtoIn cartItemDtoIn){
        return itemService.createItemInCart(cartId, cartItemDtoIn);
    }

    @DeleteMapping("/{id}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemsFromCart(@PathVariable("id") String cartId){
        Long countDeleted = itemService.deleteAllItemsFromCart(cartId);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemFromCart(@PathVariable("cartId") String cartId, @PathVariable("itemId") String itemId){
        itemService.deleteItemFromCart(cartId, itemId);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartNotFoundException(CartNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemInCartException.class)
    public ResponseEntity<ErrorResponse> handleItemInCartException(ItemInCartException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
