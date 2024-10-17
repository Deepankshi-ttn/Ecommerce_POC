package com.ecommerce.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SellerDetailsDto {
    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
    )
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
    )
    private String confirmPassword;

    @NotBlank
    private String gst;

    @NotBlank
    private String companyName;

    @NotBlank
    @Size(min=10,max = 10)
    private String companyContact;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    private String addressLine;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String label;
}
