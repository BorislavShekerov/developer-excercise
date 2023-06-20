package com.yostoya.shoptill.domain.dto;

public record InvalidFieldDto(
        String property,
        String message
) {

}
