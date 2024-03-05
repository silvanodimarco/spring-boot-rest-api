package com.silvanodimarco.springbootrestapi.api.mapper;

import com.silvanodimarco.springbootrestapi.api.dto.ReviewCreateRequestDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewResponseDto;
import com.silvanodimarco.springbootrestapi.api.dto.ReviewUpdateRequestDto;
import com.silvanodimarco.springbootrestapi.persistance.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    ReviewResponseDto reviewToReviewResponseDto(Review review);

    Review reviewCreateRequestDtoToReview(ReviewCreateRequestDto reviewCreateRequestDto);

    @Named("mapActiveIntegerToBoolean")
    public static Boolean mapActive(Integer active) {
        Boolean valor = false;
        if(active == 1) {
            valor = true;
        }
        return valor;
    }
}
