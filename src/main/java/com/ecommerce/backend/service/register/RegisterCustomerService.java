package com.ecommerce.backend.service.register;

import com.ecommerce.backend.dto.request.CustomerDetailsDto;

public interface RegisterCustomerService {
    void verify(String token);

    void resend_verify(String email);

    void createCustomer(CustomerDetailsDto customer);
}
