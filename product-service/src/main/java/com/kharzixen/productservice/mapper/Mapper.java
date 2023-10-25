package com.kharzixen.productservice.mapper;

public interface Mapper<A,B> {
    B mapToDto(A a);
    A mapFromDto(B b);
}
