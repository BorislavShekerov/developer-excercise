package com.yostoya.shoptill.mapper;

import com.yostoya.shoptill.domain.dto.InvalidFieldDto;
import jakarta.validation.ConstraintViolation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface ViolationMapper {

    @Mapping(target = "property", source = "propertyPath", qualifiedByName = "pathToString")
    InvalidFieldDto toDto(ConstraintViolation<?> violation);
}
