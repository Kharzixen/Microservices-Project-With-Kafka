package com.kharzixen.cartservice.controller;

import com.kharzixen.cartservice.dto.request.ItemDtoIn;
import com.kharzixen.cartservice.dto.response.ItemDtoOutDetailed;
import com.kharzixen.cartservice.error_handling.errors.ErrorResponse;
import com.kharzixen.cartservice.error_handling.exceptions.BadQuantityException;
import com.kharzixen.cartservice.error_handling.exceptions.CartAlreadyExistsException;
import com.kharzixen.cartservice.error_handling.exceptions.ItemNotFoundException;
import com.kharzixen.cartservice.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDtoOutDetailed> patchItem(@PathVariable("itemId") String itemId, @RequestBody ItemDtoIn itemDtoIn){
        ItemDtoOutDetailed out = itemService.patchItem(itemId, itemDtoIn);
        return new ResponseEntity<>(out, HttpStatus.OK);

    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadQuantityException.class)
    public ResponseEntity<ErrorResponse> handleBadQuantityException(BadQuantityException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
