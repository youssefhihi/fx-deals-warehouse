package com.progressoft.fxDeal.service;
import com.progressoft.fxDeal.dto.FxDealRequestDto;
import com.progressoft.fxDeal.dto.FxDealResponseDto;
import com.progressoft.fxDeal.entity.FxDealEntity;
import com.progressoft.fxDeal.exception.DealAlreadyExistException;
import com.progressoft.fxDeal.exception.InvalidCurrencyException;
import com.progressoft.fxDeal.mapper.FxDealMapper;
import com.progressoft.fxDeal.repository.FxDealRepository;
import com.progressoft.fxDeal.service.impl.FxDealServiceImpl;
import com.progressoft.fxDeal.service.validation.CurrencyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@ExtendWith(SpringExtension.class)
class FxDealServiceImplTest {

    @Mock
    private FxDealRepository fxDealRepository;

    @Mock
    private FxDealMapper dealMapper;

    @Mock
    private CurrencyValidator currencyValidator;

    @InjectMocks
    private FxDealServiceImpl fxDealService;

    @Test
    void givenInvalidCurrencies_whenSave_thenThrowInvalidCurrencyException() {
        final String invalidCurrency = "usd";
        final String exceptionMessage = String.format(
                "Invalid deal: 'fromCurrency' and 'toCurrency' must be different. Both were '%s'.", invalidCurrency);

        final FxDealRequestDto request = new FxDealRequestDto(
                "deal-001", invalidCurrency, invalidCurrency, BigDecimal.TEN
        );

        willThrow(new InvalidCurrencyException(exceptionMessage))
                .given(currencyValidator).validateCurrencyRequest(invalidCurrency, invalidCurrency);

        assertThatExceptionOfType(InvalidCurrencyException.class)
                .isThrownBy(() -> fxDealService.save(request))
                .withMessage(exceptionMessage);
    }


    @Test
    void givenExistingDealId_whenSave_thenThrowDealAlreadyExistException() {
        final FxDealRequestDto request = new FxDealRequestDto(
                "deal-002", "USD", "EUR", BigDecimal.valueOf(1000)
        );

        given(fxDealRepository.existsById(request.id()))
                .willReturn(true);

        assertThatExceptionOfType(DealAlreadyExistException.class)
                .isThrownBy(() -> fxDealService.save(request))
                .withMessage("The given ID is already exists");
    }

    @Test
    void givenValidRequest_whenSave_thenReturnFxDealResponseDto() {
        final FxDealRequestDto request = new FxDealRequestDto(
                "deal-003", "USD", "MAD", BigDecimal.valueOf(2500)
        );

        final FxDealEntity entity = new FxDealEntity(
                request.id(),
                request.fromCurrency(),
                request.toCurrency(),
                null,
                request.amount()
        );

        final FxDealResponseDto expectedResponse = new FxDealResponseDto(
                entity.getId(),
                entity.getFromCurrency(),
                entity.getToCurrency(),
                entity.getDealTimestamp(),
                entity.getAmount()
        );

        given(fxDealRepository.existsById(request.id())).willReturn(false);
        given(dealMapper.toEntity(request)).willReturn(entity);
        given(fxDealRepository.save(entity)).willReturn(entity);
        given(dealMapper.toDto(entity)).willReturn(expectedResponse);

        FxDealResponseDto actual = fxDealService.save(request);

        assertThat(actual).isNotNull();
        assertThat(actual.id()).isEqualTo(expectedResponse.id());
        assertThat(actual.fromCurrency()).isEqualTo(expectedResponse.fromCurrency());
        assertThat(actual.toCurrency()).isEqualTo(expectedResponse.toCurrency());
        assertThat(actual.amount()).isEqualTo(expectedResponse.amount());
    }
}
