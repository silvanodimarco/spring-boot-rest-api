package com.silvanodimarco.springbootrestapi.persistance.repository;

import com.silvanodimarco.springbootrestapi.persistance.model.Product;
import com.silvanodimarco.springbootrestapi.persistance.model.Review;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class ReviewSpecification {
    public static Specification<Review> filterByRatingAndOrActive(Integer rating, Boolean active) {
        return (root, query, criteriaBuilder) -> {
            Predicate ratingPredicate = rating != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating) : null;
            Predicate activePredicate = active != null ? criteriaBuilder.equal(root.get("active"), active) : null;

            List<Predicate> predicates = new ArrayList<>();

            if(ratingPredicate != null) {
                predicates.add(ratingPredicate);
            }
            if(activePredicate != null) {
                predicates.add(activePredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<Review> isFromProductWithNameLike(String productName) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, Product> reviewsProduct = root.join("product");
            return criteriaBuilder.like(reviewsProduct.get("name"),
                productName != null ? likePattern(productName) : likePattern(""));
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }
}
