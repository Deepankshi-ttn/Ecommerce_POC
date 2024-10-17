package com.ecommerce.backend.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)   //IDENTITY- if the database auto-increments IDs.
//    private Long id;

    @ManyToOne
    private Role role;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)//FetchType.LAZY for performance optimization unless you specifically need eager loading.
    private List<Address> address;

    @Id
   // @NotNull
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    @Column(unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isDeleted;

    private String modifiedBy;

    private String createdBy;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(columnDefinition = "Boolean default false")// @Column(nullable = false)
    private Boolean isActive= false;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isExpired=false;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isLocked=false;

    @Column(columnDefinition = "Integer default 0")
    private Integer invalidAttemptCount;

    private LocalDateTime lockTime;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime passwordUpdatedDate;
}
