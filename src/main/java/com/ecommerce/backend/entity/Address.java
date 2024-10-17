package com.ecommerce.backend.entity;


import lombok.*;

import javax.persistence.*;
@Builder
@Entity
@Getter
@Setter
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private String zipCode;
    private String label;
}
