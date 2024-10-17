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
@Table (name = "email_verification_token")
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

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
    public EmailVerificationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.expireAt = LocalDateTime.now().plusHours(1);
        this.user = user;
    }
}
