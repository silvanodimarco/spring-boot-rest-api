package com.silvanodimarco.springbootrestapi.persistance.repository;

import com.silvanodimarco.springbootrestapi.persistance.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM product p WHERE p.name LIKE %:name%", nativeQuery = true)
    List<Product> getProductsFilteredByName(
        @Param("name") String name
    );

    List<Product> findAllByBrandId(Integer brandId);
}
