package com.ecommerce.backend.service.admin;

import com.ecommerce.backend.dto.request.CustomerDto;
import com.ecommerce.backend.dto.request.SellerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;

import java.util.List;

public interface AdminService {
    List<CustomerDto> customerDtoList(Pageable pageRequest, String email);

    List<SellerDto> sellerDtoList(Pageable pageRequest, String email);

    String ActivateCustomer(String email) throws MailException,InterruptedException;

    String DeactivateCustomer(String email)throws MailException,InterruptedException;

    String ActivateSeller(String email)throws MailException,InterruptedException;

    String DeactivateSeller(String email)throws MailException,InterruptedException;
}
