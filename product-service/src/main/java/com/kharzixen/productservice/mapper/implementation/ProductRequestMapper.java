package com.kharzixen.productservice.mapper.implementation;

import com.kharzixen.productservice.dto.ProductDtoIn;
import com.kharzixen.productservice.mapper.Mapper;
import com.kharzixen.productservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductRequestMapper implements Mapper<Product, ProductDtoIn> {

    private final ModelMapper modelMapper;

    @Override
    public ProductDtoIn mapToDto(Product product) {
       return modelMapper.map(product, ProductDtoIn.class);
    }

    @Override
    public Product mapFromDto(ProductDtoIn productDtoIn) {
        return modelMapper.map(productDtoIn, Product.class);

    }
}
