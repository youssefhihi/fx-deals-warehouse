package com.progressoft.fxDeal.service;

import com.progressoft.fxDeal.dto.FxDealRequestDto;
import com.progressoft.fxDeal.dto.FxDealResponseDto;

public interface FxDealService {
    FxDealResponseDto save(FxDealRequestDto dto);
}
