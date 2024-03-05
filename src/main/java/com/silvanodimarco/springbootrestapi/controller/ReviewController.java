package com.silvanodimarco.springbootrestapi.controller;

import com.silvanodimarco.springbootrestapi.api.dto.ProductWithoutBrandResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.api.validation.ReviewCreate;
import com.silvanodimarco.springbootrestapi.api.validation.ReviewUpdate;
import com.silvanodimarco.springbootrestapi.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/v1/reviews"})
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews(
        @RequestParam(required = false) Integer rating,
        @RequestParam(required = false) Boolean active,
        @RequestParam(required = false) String productName
    ) {
        return new ResponseEntity<>(
            reviewService.getAllReviews(rating, active, productName),
            HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<ReviewResponseDto> createReview(
        @Validated(ReviewCreate.class)
        @RequestBody ReviewCreateRequestDto reviewCreateRequestDto
    ) {
        return new ResponseEntity<>(
            reviewService.createReview(reviewCreateRequestDto),
            HttpStatus.CREATED
        );
    }

    @PutMapping("{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
        @PathVariable Integer reviewId,
        @Validated(ReviewUpdate.class)
        @RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto
    ) {
        return new ResponseEntity<>(
            reviewService.updateReview(reviewId, reviewUpdateRequestDto),
            HttpStatus.OK
        );
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity deleteReview(
        @PathVariable Integer reviewId
    ) {
       reviewService.deleteReview(reviewId);
       return new ResponseEntity<>(
           HttpStatus.OK
       );
    }
}
