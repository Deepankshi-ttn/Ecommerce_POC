package com.ecommerce.backend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailVerificationFailedException extends RuntimeException {
    public EmailVerificationFailedException(String message) {
        super(message);
    }
}
