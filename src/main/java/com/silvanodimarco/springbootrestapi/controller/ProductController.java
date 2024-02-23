package com.silvanodimarco.springbootrestapi.controller;

import com.silvanodimarco.springbootrestapi.api.dto.ProductCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductWithBrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductWithoutBrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.validation.ProductCreate;
import com.silvanodimarco.springbootrestapi.api.validation.ProductUpdate;
import com.silvanodimarco.springbootrestapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/v1/products"})
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}/without-brand")
    public ResponseEntity<ProductWithoutBrandResponseDto> getProductWithoutBrandById(
        @PathVariable Integer productId
    ) {
        return new ResponseEntity<>(
            productService.getProductWithoutBrandById(productId),
            HttpStatus.OK
        );
    }

    @GetMapping("/{productId}/with-brand")
    public ResponseEntity<ProductWithBrandResponseDto> getProductWithBrandById(
        @PathVariable Integer productId
    ) {
        return new ResponseEntity<>(
            productService.getProductWithBrandById(productId),
            HttpStatus.OK
        );
    }

    @GetMapping("/without-brand")
    public ResponseEntity<List<ProductWithoutBrandResponseDto>> getAllProductsWithoutBrand(
        @RequestParam(required = false) String name
    ) {
        return new ResponseEntity<>(
            productService.getAllProductsWithoutBrand(name),
            HttpStatus.OK
        );
    }

    @GetMapping("/with-brand")
    public ResponseEntity<List<ProductWithBrandResponseDto>> getAllProductsWithBrand(
        @RequestParam(required = false) String name
    ) {
        return new ResponseEntity<>(
            productService.getAllProductsWithBrand(name),
            HttpStatus.OK
        );
    }

    @GetMapping("/by-brand/{brandId}")
    public ResponseEntity<List<ProductWithoutBrandResponseDto>> getAllProductsByBrand(
        @PathVariable Integer brandId
    ) {
        return new ResponseEntity<>(
            productService.getAllProductsByBrand(brandId),
            HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<ProductWithBrandResponseDto> createProduct(
        @Validated(ProductCreate.class)
        @RequestBody ProductCreateRequestDto productCreateRequestDto
    ) {
        return new ResponseEntity<>(
            productService.createProduct(productCreateRequestDto),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductWithBrandResponseDto> updateProduct(
        @PathVariable Integer productId,
        @Validated(ProductUpdate.class)
        @RequestBody ProductUpdateRequestDto productUpdateRequestDto
    ) {
        return new ResponseEntity<>(
            productService.updateProduct(productId, productUpdateRequestDto),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(
        @PathVariable Integer productId
    ) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(
            HttpStatus.OK
        );
    }
}
