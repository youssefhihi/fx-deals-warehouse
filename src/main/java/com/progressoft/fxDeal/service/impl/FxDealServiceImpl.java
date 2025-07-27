package com.progressoft.fxDeal.service.impl;

import com.progressoft.fxDeal.dto.FxDealRequestDto;
import com.progressoft.fxDeal.dto.FxDealResponseDto;
import com.progressoft.fxDeal.entity.FxDealEntity;
import com.progressoft.fxDeal.exception.DealAlreadyExistException;
import com.progressoft.fxDeal.mapper.FxDealMapper;
import com.progressoft.fxDeal.repository.FxDealRepository;
import com.progressoft.fxDeal.service.FxDealService;
import com.progressoft.fxDeal.service.validation.CurrencyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {

    private final FxDealRepository fxDealRepository;
    private final FxDealMapper dealMapper;
    private final CurrencyValidator currencyValidator;

    @Override
    public FxDealResponseDto save(FxDealRequestDto dto) {
        currencyValidator.validateCurrencyRequest(dto.fromCurrency(), dto.toCurrency());
        if (fxDealRepository.existsById(dto.id())) {
            log.warn("Duplicate deal ID '{}' - save operation skipped.", dto.id());
            throw new DealAlreadyExistException("The given ID is already exists");
        }

        final FxDealEntity deal = dealMapper.toEntity(dto);
        final FxDealEntity savedDeal = fxDealRepository.save(deal);

        return dealMapper.toDto(savedDeal);
    }
}
