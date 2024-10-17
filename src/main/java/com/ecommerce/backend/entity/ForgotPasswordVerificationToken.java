package com.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    public String token;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime expireAt;

    @OneToOne
    private User user;

    @Transient
    private boolean isExpired;

    public boolean isExpired() {
        return getExpireAt().isBefore(LocalDateTime.now());
    }

    public ForgotPasswordVerificationToken(User user){
        this.token= UUID.randomUUID().toString();
        this.expireAt = LocalDateTime.now().plusMinutes(15);
        this.user=user;
    }
}
