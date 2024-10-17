package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.EmailVerificationToken;
import com.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerificationToken,Long> {
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findByUser(User user);
}
