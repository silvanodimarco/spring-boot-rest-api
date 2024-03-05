package com.silvanodimarco.springbootrestapi.service;

import com.silvanodimarco.springbootrestapi.api.dto.ReviewCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.api.mapper.ReviewMapper;
import com.silvanodimarco.springbootrestapi.exception.ProductNotFoundException;
import com.silvanodimarco.springbootrestapi.exception.ReviewNotFoundException;
import com.silvanodimarco.springbootrestapi.persistance.model.Product;
import com.silvanodimarco.springbootrestapi.persistance.model.Review;
import com.silvanodimarco.springbootrestapi.persistance.repository.ProductRepository;
import com.silvanodimarco.springbootrestapi.persistance.repository.ReviewRepository;
import com.silvanodimarco.springbootrestapi.persistance.repository.ReviewSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public List<ReviewResponseDto> getAllReviews(Integer rating, Boolean active, String productName) {
        final Specification<Review> filterByRatingAndOrActive = ReviewSpecification.filterByRatingAndOrActive(rating, active);
        final Specification<Review> isFromProductWithNameLike = ReviewSpecification.isFromProductWithNameLike(productName);
        Specification<Review> specification;

        if(rating != null || active != null) {
            specification = filterByRatingAndOrActive.and(isFromProductWithNameLike);
        } else {
            specification = isFromProductWithNameLike;
        }

        List<Review> reviews = reviewRepository.findAll(specification);
        List<ReviewResponseDto> reviewsDto = new ArrayList<>();

        for(Review review : reviews) {
            reviewsDto.add(ReviewMapper.INSTANCE.reviewToReviewResponseDto(review));
        }

        return reviewsDto;
    }

    public ReviewResponseDto createReview(ReviewCreateRequestDto reviewCreateRequestDto) {
        Product product = productRepository.findById(reviewCreateRequestDto.productId())
            .orElseThrow(() -> new ProductNotFoundException("id", reviewCreateRequestDto.productId()));

        Review review = ReviewMapper.INSTANCE.reviewCreateRequestDtoToReview(reviewCreateRequestDto);
        review.setActive(true);
        review.setProduct(product);

        reviewRepository.save(review);

        return ReviewMapper.INSTANCE.reviewToReviewResponseDto(review);
    }

    public ReviewResponseDto updateReview(Integer reviewId, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("id", reviewId));

        if(reviewUpdateRequestDto.content() != null) {
            review.setContent(reviewUpdateRequestDto.content());
        }
        if(reviewUpdateRequestDto.rating() != null) {
            review.setRating(reviewUpdateRequestDto.rating());
        }
        if(reviewUpdateRequestDto.active() != null) {
            review.setActive(reviewUpdateRequestDto.active());
        }

        reviewRepository.save(review);

        return ReviewMapper.INSTANCE.reviewToReviewResponseDto(review);
    }

    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException("id", reviewId));

        reviewRepository.delete(review);
    }
}
