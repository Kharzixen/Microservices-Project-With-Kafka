package com.kharzixen.productservice.service;

import com.kharzixen.productservice.dto.ProductDtoIn;
import com.kharzixen.productservice.dto.ProductDtoOut;
import com.kharzixen.productservice.mapper.implementation.ProductRequestMapper;
import com.kharzixen.productservice.mapper.implementation.ProductResponseMapper;
import com.kharzixen.productservice.model.Product;
import com.kharzixen.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Log
@AllArgsConstructor
public class ProductService {

    private final ProductResponseMapper productResponseMapper;
    private final ProductRequestMapper productRequestMapper;
    private final ProductRepository productRepository;

    public ProductDtoOut createProduct(ProductDtoIn productDtoIn){
        Product product = productRequestMapper.mapFromDto(productDtoIn);
        Product savedProduct =  productRepository.save(product);
        return productResponseMapper.mapToDto(savedProduct);

    }

    public List<ProductDtoOut> getAllProducts(){
        return productRepository.findAll().stream().map(productResponseMapper::mapToDto).toList();
    }

    public Optional<ProductDtoOut> getProductById(String id){
        Optional<Product> optional =  productRepository.findById(id);
        return optional.map(productResponseMapper::mapToDto);
    }

    @Transactional
    public void deleteProduct(String id){
        productRepository.deleteById(id);
    }

    @Transactional
    public Optional<Product> updateProductInventory(String productId, int inventory){
        Optional<Product> optionalProduct =  productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setInventory(inventory);
            Product saved = productRepository.save(product);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }
}
