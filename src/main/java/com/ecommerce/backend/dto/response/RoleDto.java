package com.ecommerce.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleDto {
    private Long id;
    private String authority;
    private Boolean isDeleted;
}
