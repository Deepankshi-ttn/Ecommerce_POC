package com.ecommerce.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

    @Component
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class CustomerDto {

        private Long id;
        private String email;
        private String firstName;
        private String middleName;
        private String lastName;
        private boolean isActive=false;
        private String contact;
        //private String image;
    }

