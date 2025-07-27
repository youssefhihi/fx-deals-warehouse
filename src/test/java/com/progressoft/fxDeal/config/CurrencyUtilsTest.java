package com.progressoft.fxDeal.config;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class CurrencyUtilsTest {
    @Test
    void givenValidCurrencyCode_whenIsExist_thenReturnTrue() {
        assertThat(CurrencyUtils.isExist("USD")).isTrue();
        assertThat(CurrencyUtils.isExist("eur")).isTrue();
        assertThat(CurrencyUtils.isExist("jPy")).isTrue();
    }

    @Test
    void givenInvalidCurrencyCode_whenIsExist_thenReturnFalse() {
        assertThat(CurrencyUtils.isExist("XYZ")).isFalse();
        assertThat(CurrencyUtils.isExist("US")).isFalse();
        assertThat(CurrencyUtils.isExist("USDA")).isFalse();
    }

    @Test
    void givenNullCurrencyCode_whenIsExist_thenReturnFalse() {
        assertThat(CurrencyUtils.isExist(null)).isFalse();
    }

    @Test
    void givenEmptyCurrencyCode_whenIsExist_thenReturnFalse() {
        assertThat(CurrencyUtils.isExist("")).isFalse();
        assertThat(CurrencyUtils.isExist("   ")).isFalse();
    }
}
