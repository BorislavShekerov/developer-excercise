package com.yostoya.shoptill.mapper;

import com.yostoya.shoptill.domain.Item;
import com.yostoya.shoptill.domain.dto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto toDto(Item item);

    @Mapping(target = "id", ignore = true)
    Item toItem(ItemDto dto);

    @Mapping(target = "id", ignore = true)
    Item update(ItemDto dto, @MappingTarget Item item);


}
