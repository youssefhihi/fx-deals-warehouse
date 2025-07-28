package com.progressoft.fxDeal.service.validation.impl;

import com.progressoft.fxDeal.config.CurrencyUtils;
import com.progressoft.fxDeal.exception.CurrencyNotFoundException;
import com.progressoft.fxDeal.exception.InvalidCurrencyException;
import com.progressoft.fxDeal.service.validation.CurrencyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyValidatorImpl implements CurrencyValidator {


    private final String CURRENCYPATTERN = "^[A-Z]{3}$";


    @Override
    public void validateCurrencyRequest(String fromCurrency, String toCurrency) {
        checkPattern(fromCurrency, toCurrency);
        checkCurrencyExistence(toCurrency);
        checkCurrencyExistence(fromCurrency);
        validateCurrencyDifferentiation(fromCurrency, toCurrency);
    }

    private void validateCurrencyDifferentiation(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            log.warn("Currency differentiation validation failed: fromCurrency and toCurrency are the same ('{}')", fromCurrency);
            String errorMessage = String.format(
                    "Invalid deal: 'fromCurrency' and 'toCurrency' must be different. Both were '%s'.", fromCurrency);
            throw new InvalidCurrencyException(errorMessage);
        }
    }

    private void checkCurrencyExistence(String value) {
        if (!CurrencyUtils.isExist(value)) {
            log.warn("Currency existence check failed: '{}' is not supported", value);
            String errorMessage = String.format("Currency '%s' is not supported.", value);
            throw new CurrencyNotFoundException(errorMessage);
        }
    }

    private void checkPattern(String fromCurrency, String toCurrency) {
        if (isNotMatches(fromCurrency)) {
            log.warn("Invalid format for fromCurrency: '{}'", fromCurrency);
            throw new InvalidCurrencyException(
                    String.format("Invalid format for 'fromCurrency' code: '%s'. Expected a valid currency code.", fromCurrency));
        }
        if (isNotMatches(toCurrency)) {
            log.warn("Invalid format for toCurrency: '{}'", toCurrency);
            throw new InvalidCurrencyException(
                    String.format("Invalid format for 'toCurrency' code: '%s'. Expected a valid currency code.", toCurrency));
        }
    }

    private boolean isNotMatches(String currencyCode) {
        return currencyCode == null || !currencyCode.trim().matches(CURRENCYPATTERN);
    }

}
