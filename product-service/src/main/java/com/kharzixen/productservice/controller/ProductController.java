package com.kharzixen.productservice.controller;

import com.kharzixen.productservice.dto.ProductDtoIn;
import com.kharzixen.productservice.dto.ProductDtoOut;
import com.kharzixen.productservice.error_handling.errors.ErrorResponse;
import com.kharzixen.productservice.error_handling.exceptions.ProductNotFoundException;
import com.kharzixen.productservice.model.Product;
import com.kharzixen.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Log
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDtoOut> createProduct(@RequestBody ProductDtoIn productDtoIn){
        log.severe("Post request");
        ProductDtoOut responseDto = productService.createProduct(productDtoIn);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDtoOut>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoOut> getProductById(@PathVariable("id") String id){
        Optional<ProductDtoOut> optionalResponse = productService.getProductById(id);
        if(optionalResponse.isPresent()){
            return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable("id") String id){
        productService.deleteProduct(id);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
