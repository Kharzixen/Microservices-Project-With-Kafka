package com.kharzixen.productservice.mapper.implementation;

import com.kharzixen.productservice.dto.ProductDtoOut;
import com.kharzixen.productservice.mapper.Mapper;
import com.kharzixen.productservice.model.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductResponseMapper implements Mapper<Product, ProductDtoOut> {

    private final ModelMapper modelMapper;

    @Override
    public ProductDtoOut mapToDto(Product product) {
        return modelMapper.map(product, ProductDtoOut.class);
    }

    @Override
    public Product mapFromDto(ProductDtoOut productDtoOut) {
        return modelMapper.map(productDtoOut, Product.class);
    }
}
