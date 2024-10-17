package com.ecommerce.backend.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Component
@Getter
@Setter
public class EmailDto {
    @NotBlank(
            message = "Email cannot be blank"
    )
    @javax.validation.constraints.Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email format is Incorrect"
    )
    private String email;
}
