package com.silvanodimarco.springbootrestapi.api.mapper;

import com.silvanodimarco.springbootrestapi.api.dto.ProductWithBrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductWithoutBrandResponseDto;
import com.silvanodimarco.springbootrestapi.persistance.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductWithoutBrandResponseDto productToProductWithoutBrandResponseDto(Product product);

    ProductWithBrandResponseDto productToProductWithBrandResponseDto(Product product);
}
