package com.ecommerce.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class UserDto {

    private Long id;
    private RoleDto role;
    private List<AddressDto> address;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private Boolean isDeleted;
    private String modifiedBy;
    private String createdBy;
    private Boolean isActive= false;
    private Boolean isExpired=false;
    private Boolean isLocked=false;
    private Integer invalidAttemptCount;
    private LocalDateTime lockTime;
    private LocalDateTime passwordUpdatedDate;
}
