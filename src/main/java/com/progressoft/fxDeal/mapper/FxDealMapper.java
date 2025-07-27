package com.progressoft.fxDeal.mapper;

import com.progressoft.fxDeal.dto.FxDealRequestDto;
import com.progressoft.fxDeal.dto.FxDealResponseDto;
import com.progressoft.fxDeal.entity.FxDealEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FxDealMapper {

    FxDealEntity toEntity(FxDealRequestDto dto);

    FxDealResponseDto toDto(FxDealEntity entity);
}
