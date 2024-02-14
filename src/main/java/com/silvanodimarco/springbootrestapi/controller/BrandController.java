package com.silvanodimarco.springbootrestapi.controller;

import com.silvanodimarco.springbootrestapi.api.dto.BrandCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.BrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.BrandUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.api.validation.BrandCreate;
import com.silvanodimarco.springbootrestapi.api.validation.BrandUpdate;
import com.silvanodimarco.springbootrestapi.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/v1/brands"})
@Validated
public class BrandController {
    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandResponseDto> getBrandById(
        @PathVariable Integer brandId
    ) {
        return new ResponseEntity<>(
            brandService.getBrandById(brandId),
            HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<List<BrandResponseDto>> getAllBrands() {
        return new ResponseEntity<>(
            brandService.getAllBrands(),
            HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<BrandResponseDto> createBrand(
        @Validated(BrandCreate.class)
        @RequestBody BrandCreateRequestDto brandCreateRequestDto
    ) {
        return new ResponseEntity<>(
            brandService.createBrand(brandCreateRequestDto),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandResponseDto> updateBrand(
        @PathVariable Integer brandId,
        @Validated(BrandUpdate.class)
        @RequestBody BrandUpdateRequestDto brandUpdateRequestDto
    ) {
        return new ResponseEntity<>(
            brandService.updateBrand(brandId, brandUpdateRequestDto),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity deleteBrand(
        @PathVariable Integer brandId
    ) {
        brandService.deleteBrand(brandId);
        return new ResponseEntity<>(
            HttpStatus.OK
        );
    }
}
