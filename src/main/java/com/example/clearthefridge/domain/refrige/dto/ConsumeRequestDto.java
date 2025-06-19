package com.example.clearthefridge.domain.refrige.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumeRequestDto {
    private Long ingredientId;
    private int amount;
}
