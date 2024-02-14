package com.silvanodimarco.springbootrestapi.service;

import com.silvanodimarco.springbootrestapi.api.dto.BrandCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.BrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.BrandUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.api.mapper.BrandMapper;
import com.silvanodimarco.springbootrestapi.exception.BrandNotFoundException;
import com.silvanodimarco.springbootrestapi.exception.BrandNotUniqueException;
import com.silvanodimarco.springbootrestapi.exception.error.ResourceNotUniqueException;
import com.silvanodimarco.springbootrestapi.persistance.model.Brand;
import com.silvanodimarco.springbootrestapi.persistance.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public BrandResponseDto getBrandById(Integer brandId) {
        Brand brand = brandRepository.findById(brandId)
            .orElseThrow(()-> new BrandNotFoundException("id", brandId));

        return BrandMapper.INSTANCE.brandToBrandResponseDto(brand);
    }

    public List<BrandResponseDto> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandResponseDto> brandsDto = new ArrayList<>();

        for(Brand brand : brands) {
            brandsDto.add(BrandMapper.INSTANCE.brandToBrandResponseDto(brand));
        }

        return brandsDto;
    }

    public BrandResponseDto createBrand(BrandCreateRequestDto brandCreateRequestDto) {
        Brand existingBrand = brandRepository.findByName(brandCreateRequestDto.name());

        if(existingBrand != null) {
            throw new BrandNotUniqueException("name", existingBrand.getName());
        }

        Brand brand = BrandMapper.INSTANCE.brandCreateRequestDtoToBrand(brandCreateRequestDto);
        brand = brandRepository.save(brand);

        return BrandMapper.INSTANCE.brandToBrandResponseDto(brand);
    }

    public BrandResponseDto updateBrand(Integer brandId, BrandUpdateRequestDto brandUpdateRequestDto) {
        Brand brand = brandRepository.findById(brandId)
            .orElseThrow(()-> new BrandNotFoundException("id", brandId));

        if(brandUpdateRequestDto.name() != null) {
            Brand existingBrand = brandRepository.findByName(brandUpdateRequestDto.name());
            if(existingBrand != null) {
                throw new BrandNotUniqueException("name", existingBrand.getName());
            }

            brand.setName(brandUpdateRequestDto.name());
        }

        brandRepository.save(brand);

        return BrandMapper.INSTANCE.brandToBrandResponseDto(brand);
    }

    public void deleteBrand(Integer brandId) {
        Brand brand = brandRepository.findById(brandId)
            .orElseThrow(()-> new BrandNotFoundException("id", brandId));

        brandRepository.delete(brand);
    }
}
