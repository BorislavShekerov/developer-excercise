package com.yostoya.shoptill.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@JsonInclude(NON_DEFAULT)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class ItemDto {

        private Long id;
        private  @NotBlank String name;
        private  @NotNull @Positive BigDecimal price;
        private  Boolean halfPrice;
}
