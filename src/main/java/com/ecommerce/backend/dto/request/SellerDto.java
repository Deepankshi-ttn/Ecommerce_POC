package com.ecommerce.backend.dto.request;

import com.ecommerce.backend.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gst;
    private String companyName;
    private String companyContact;
    private Address address;
    private boolean isActive=false;
    //private String image;
}
