package com.silvanodimarco.springbootrestapi.api.mapper;

import com.silvanodimarco.springbootrestapi.api.dto.BrandCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.BrandResponseDto;
import com.silvanodimarco.springbootrestapi.persistance.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    Brand brandCreateRequestDtoToBrand(BrandCreateRequestDto brandCreateRequestDto);

    BrandResponseDto brandToBrandResponseDto(Brand brand);
}
