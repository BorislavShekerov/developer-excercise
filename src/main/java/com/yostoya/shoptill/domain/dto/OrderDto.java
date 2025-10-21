package com.yostoya.shoptill.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    @NotBlank
    private String orderId;

    @NotNull
    @Positive
    private Long user;

    private List<@NotNull ItemDto> items;

    @NotBlank
    private String totalAmount;
}
