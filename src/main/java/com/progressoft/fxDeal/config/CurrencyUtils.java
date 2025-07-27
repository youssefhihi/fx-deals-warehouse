package com.progressoft.fxDeal.config;

import java.util.Currency;
import java.util.Set;

public class CurrencyUtils {
    private static final Set<String> SUPPORTED_ISO_CODES = Currency.getAvailableCurrencies().stream()
            .map(Currency::getCurrencyCode)
            .collect(java.util.stream.Collectors.toUnmodifiableSet());


    public static boolean isExist(String currencyCode) {
        if (currencyCode == null) return false;
        return SUPPORTED_ISO_CODES.contains(currencyCode.toUpperCase());
    }

}
