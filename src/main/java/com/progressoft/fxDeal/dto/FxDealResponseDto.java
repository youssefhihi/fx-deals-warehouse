package com.progressoft.fxDeal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FxDealResponseDto(
         String id,
         String fromCurrency,
         String toCurrency,
         LocalDateTime dealTimestamp,
         BigDecimal amount
) {
}
