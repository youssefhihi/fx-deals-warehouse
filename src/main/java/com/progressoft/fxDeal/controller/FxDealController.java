package com.progressoft.fxDeal.controller;

import com.progressoft.fxDeal.dto.FxDealRequestDto;
import com.progressoft.fxDeal.dto.FxDealResponseDto;
import com.progressoft.fxDeal.service.FxDealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
public class FxDealController {

    private final FxDealService fxDealService;

    @PostMapping
    public ResponseEntity<FxDealResponseDto> createDeal (@Valid @RequestBody FxDealRequestDto dto ) {
        FxDealResponseDto savedDeal = fxDealService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeal);
    }
}
