package com.silvanodimarco.springbootrestapi.persistance.repository;

import com.silvanodimarco.springbootrestapi.persistance.model.Review;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    List<Review> findAll(Specification<Review> specification);
}
