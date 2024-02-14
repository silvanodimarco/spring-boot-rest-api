package com.silvanodimarco.springbootrestapi.persistance.repository;

import com.silvanodimarco.springbootrestapi.persistance.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findByName(String name);
}
