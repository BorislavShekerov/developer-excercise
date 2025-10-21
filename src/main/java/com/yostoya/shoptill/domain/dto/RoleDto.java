package com.yostoya.shoptill.domain.dto;

import com.yostoya.shoptill.domain.Permission;
import com.yostoya.shoptill.domain.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;


public record RoleDto(

        @NotBlank
        Role name,

        @NotBlank
        Set<Permission> permissions
) {

}
