package com.ecommerce.backend.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class TokenExpired  extends RuntimeException{


    public TokenExpired(String message){
        super(message);
    }
}
