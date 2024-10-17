package com.ecommerce.backend.controllers.register;

import com.ecommerce.backend.dto.request.SellerDetailsDto;
import com.ecommerce.backend.dto.response.UserDto;
import com.ecommerce.backend.service.register.RegisterSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterSellerController {
    @Autowired
    private RegisterSellerService registerSellerService;

    @PostMapping("/seller")
    public ResponseEntity<UserDto> createSeller(@RequestBody @Valid SellerDetailsDto seller) {
        System.out.println(seller.toString());
        registerSellerService.createSeller(seller);
        return ResponseEntity.ok().build();
    }
}
