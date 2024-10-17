package com.ecommerce.backend.service.register;

import com.ecommerce.backend.dto.request.SellerDetailsDto;

public interface RegisterSellerService {
    void createSeller(SellerDetailsDto seller);
}
