package com.ecommerce.backend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressDto {
    private Long id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private String zipCode;
    private String label;
}
