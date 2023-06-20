package com.yostoya.shoptill.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Item extends BaseDomain {

    @NotBlank
    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String name;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    private boolean isHalfPrice;
}
