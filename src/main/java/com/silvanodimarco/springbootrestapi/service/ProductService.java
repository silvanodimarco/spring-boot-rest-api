package com.silvanodimarco.springbootrestapi.service;

import com.silvanodimarco.springbootrestapi.api.dto.ProductCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductWithBrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ProductWithoutBrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.mapper.ProductMapper;
import com.silvanodimarco.springbootrestapi.exception.BrandNotFoundException;
import com.silvanodimarco.springbootrestapi.exception.ProductNotFoundException;
import com.silvanodimarco.springbootrestapi.persistance.model.Brand;
import com.silvanodimarco.springbootrestapi.persistance.model.Product;
import com.silvanodimarco.springbootrestapi.persistance.repository.BrandRepository;
import com.silvanodimarco.springbootrestapi.persistance.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

    public ProductWithoutBrandResponseDto getProductWithoutBrandById(Integer productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("id", productId));

        return ProductMapper.INSTANCE.productToProductWithoutBrandResponseDto(product);
    }

    public ProductWithBrandResponseDto getProductWithBrandById(Integer productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("id", productId));

        return ProductMapper.INSTANCE.productToProductWithBrandResponseDto(product);
    }

    public List<ProductWithoutBrandResponseDto> getAllProductsWithoutBrand(String name) {
        List<Product> products;

        if(name != null) {
            products = productRepository.getProductsFilteredByName(name);
        } else {
            products = productRepository.findAll();
        }

        List<ProductWithoutBrandResponseDto> productsDto = new ArrayList<>();

        for(Product product : products) {
            productsDto.add(
                ProductMapper.INSTANCE.productToProductWithoutBrandResponseDto(product)
            );
        }

        return productsDto;
    }

    public List<ProductWithBrandResponseDto> getAllProductsWithBrand(String name) {
        List<Product> products;

        if(name != null) {
            products = productRepository.getProductsFilteredByName(name);
        } else {
            products = productRepository.findAll();
        }

        List<ProductWithBrandResponseDto> productsDto = new ArrayList<>();

        for(Product product : products) {
            productsDto.add(
                ProductMapper.INSTANCE.productToProductWithBrandResponseDto(product)
            );
        }

        return productsDto;
    }

    public List<ProductWithoutBrandResponseDto> getAllProductsByBrand(Integer brandId) {
        List<Product> products = productRepository.findAllByBrandId(brandId);

        List<ProductWithoutBrandResponseDto> productsDto = new ArrayList<>();

        for(Product product : products) {
            productsDto.add(
                ProductMapper.INSTANCE.productToProductWithoutBrandResponseDto(product)
            );
        }

        return productsDto;
    }

    public ProductWithBrandResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Brand brand = brandRepository.findById(productCreateRequestDto.brandId())
            .orElseThrow(() -> new BrandNotFoundException("id", productCreateRequestDto.brandId()));

        Product product = Product.builder()
            .name(productCreateRequestDto.name())
            .brand(brand)
            .build();

        product = productRepository.save(product);

        return ProductMapper.INSTANCE.productToProductWithBrandResponseDto(product);
    }

    public ProductWithBrandResponseDto updateProduct(Integer productId, ProductUpdateRequestDto productUpdateRequestDto) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("id", productId));

        if (productUpdateRequestDto.name() != null) {
            product.setName(productUpdateRequestDto.name());
        }

        productRepository.save(product);

        return ProductMapper.INSTANCE.productToProductWithBrandResponseDto(product);
    }

    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("id", productId));

        productRepository.delete(product);
    }
}
