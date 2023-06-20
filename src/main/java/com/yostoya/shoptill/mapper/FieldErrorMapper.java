package com.yostoya.shoptill.mapper;

import com.yostoya.shoptill.domain.dto.InvalidFieldDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.validation.FieldError;

@Mapper(componentModel = "spring")
public interface FieldErrorMapper {

    @Mapping(target = "property", source = "field")
    @Mapping(target = "message", source = "defaultMessage")
    InvalidFieldDto toDto(FieldError objectError);

}
