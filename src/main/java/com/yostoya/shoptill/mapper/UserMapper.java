package com.yostoya.shoptill.mapper;

import com.yostoya.shoptill.domain.Role;
import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.domain.dto.RegisterDto;
import com.yostoya.shoptill.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface UserMapper {
    @Mapping(target = "role", source = "role")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(userRole())")
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "email", source = "email", conditionQualifiedByName = "notRegistered")
    User toUser(RegisterDto dto);

    default Role userRole() {
        return Role.USER;
    }
}
