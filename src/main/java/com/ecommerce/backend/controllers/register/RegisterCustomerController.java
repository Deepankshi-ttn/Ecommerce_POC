package com.ecommerce.backend.controllers.register;

import com.ecommerce.backend.dto.request.CustomerDetailsDto;
import com.ecommerce.backend.dto.request.EmailDto;
import com.ecommerce.backend.dto.response.UserDto;
import com.ecommerce.backend.service.register.RegisterCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/register")
public class  RegisterCustomerController {

    @Autowired
    private RegisterCustomerService registerCustomerService;

    @PutMapping("/verify/{token}")
    public ResponseEntity<String> verify(@RequestHeader("Authorization") String token) {
        registerCustomerService.verify(token);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/re-verify")
    public ResponseEntity<String> re_send(@RequestBody EmailDto email) {
        registerCustomerService.resend_verify(email.getEmail());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/customer")
    public ResponseEntity createCustomer(@RequestBody @Valid CustomerDetailsDto customer) {
        System.out.println(customer.toString());
        registerCustomerService.createCustomer(customer);
        return ResponseEntity.ok().build();
    }
}
