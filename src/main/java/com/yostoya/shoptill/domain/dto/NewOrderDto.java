package com.yostoya.shoptill.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NewOrderDto(

        List<@NotEmpty String> items
) {
}
