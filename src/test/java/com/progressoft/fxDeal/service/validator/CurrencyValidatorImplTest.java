package com.progressoft.fxDeal.service.validator;

import com.progressoft.fxDeal.exception.CurrencyNotFoundException;
import com.progressoft.fxDeal.exception.InvalidCurrencyException;
import com.progressoft.fxDeal.service.validation.CurrencyValidator;
import com.progressoft.fxDeal.config.CurrencyUtils;
import com.progressoft.fxDeal.service.validation.impl.CurrencyValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CurrencyValidatorImplTest {

    private CurrencyValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CurrencyValidatorImpl();
    }

    @Test
    void givenSameCurrencies_whenValidate_thenThrowInvalidCurrencyException() {
        String currency = "USD";

        try (MockedStatic<CurrencyUtils> currencyUtilsMocked = org.mockito.Mockito.mockStatic(CurrencyUtils.class)) {
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(currency)).thenReturn(true);

            assertThatExceptionOfType(InvalidCurrencyException.class)
                    .isThrownBy(() -> validator.validateCurrencyRequest(currency, currency))
                    .withMessage("Invalid deal: 'fromCurrency' and 'toCurrency' must be different. Both were 'USD'.");
        }
    }

    @Test
    void givenInvalidFromCurrencyFormat_whenValidate_thenThrowInvalidCurrencyException() {
        String from = "usd1";
        String to = "EUR";

        try (MockedStatic<CurrencyUtils> currencyUtilsMocked = org.mockito.Mockito.mockStatic(CurrencyUtils.class)) {
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(to)).thenReturn(true);
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(from)).thenReturn(true);

            assertThatExceptionOfType(InvalidCurrencyException.class)
                    .isThrownBy(() -> validator.validateCurrencyRequest(from, to))
                    .withMessageContaining("Invalid format for 'fromCurrency'");
        }
    }

    @Test
    void givenInvalidToCurrencyFormat_whenValidate_thenThrowInvalidCurrencyException() {
        String from = "USD";
        String to = "e12";

        try (MockedStatic<CurrencyUtils> currencyUtilsMocked = org.mockito.Mockito.mockStatic(CurrencyUtils.class)) {
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(from)).thenReturn(true);
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(to)).thenReturn(true);

            assertThatExceptionOfType(InvalidCurrencyException.class)
                    .isThrownBy(() -> validator.validateCurrencyRequest(from, to))
                    .withMessageContaining("Invalid format for 'toCurrency'");
        }
    }

    @Test
    void givenNonExistingFromCurrency_whenValidate_thenThrowCurrencyNotFoundException() {
        String from = "ABC";
        String to = "EUR";

        try (MockedStatic<CurrencyUtils> currencyUtilsMocked = org.mockito.Mockito.mockStatic(CurrencyUtils.class)) {
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(from)).thenReturn(false);
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(to)).thenReturn(true);

            assertThatExceptionOfType(CurrencyNotFoundException.class)
                    .isThrownBy(() -> validator.validateCurrencyRequest(from, to))
                    .withMessage("Currency 'ABC' is not supported.");
        }
    }

    @Test
    void givenNonExistingToCurrency_whenValidate_thenThrowCurrencyNotFoundException() {
        String from = "USD";
        String to = "ZZZ";

        try (MockedStatic<CurrencyUtils> currencyUtilsMocked = org.mockito.Mockito.mockStatic(CurrencyUtils.class)) {
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(from)).thenReturn(true);
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(to)).thenReturn(false);

            assertThatExceptionOfType(CurrencyNotFoundException.class)
                    .isThrownBy(() -> validator.validateCurrencyRequest(from, to))
                    .withMessage("Currency 'ZZZ' is not supported.");
        }
    }

    @Test
    void givenValidCurrencies_whenValidate_thenPassesWithoutException() {
        String from = "USD";
        String to = "EUR";

        try (MockedStatic<CurrencyUtils> currencyUtilsMocked = org.mockito.Mockito.mockStatic(CurrencyUtils.class)) {
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(from)).thenReturn(true);
            currencyUtilsMocked.when(() -> CurrencyUtils.isExist(to)).thenReturn(true);

            assertThatCode(() -> validator.validateCurrencyRequest(from, to)).doesNotThrowAnyException();
        }
    }
}