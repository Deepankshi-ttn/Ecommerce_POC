package com.ecommerce.backend.controllers.admin;

import com.ecommerce.backend.dto.request.CustomerDto;
import com.ecommerce.backend.dto.request.SellerDto;
import com.ecommerce.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/admin/get-customer")
    public ResponseEntity<List<CustomerDto>> getAllCustomers (
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @Param("email") String email) {

//        logger.info("AdminController :: Retrieve all customers method executed");
        Pageable pageRequest=PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        List<CustomerDto> customerDtoList=adminService.customerDtoList(pageRequest, email);
        return new ResponseEntity<List<CustomerDto>>(customerDtoList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/admin/get-seller")
    public ResponseEntity<List<SellerDto>> getAllSeller(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @Param("email") String email) {

//        logger.info("AdminController :: Retrieve all seller method executed");
        Pageable pageRequest=PageRequest.of(pageNo,pageSize,Sort.by(sortBy));

        List<SellerDto> sellerDtoList=adminService.sellerDtoList(pageRequest,email);
        return new ResponseEntity<List<SellerDto>>(sellerDtoList, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping("activate-customer/{email}")
    public ResponseEntity<String> activateCustomer(@PathVariable String email)throws InterruptedException {
//        logger.info("Admin Controller::Activate all customer execution");
        String responseMessage=adminService.ActivateCustomer(email);
        return new ResponseEntity<>(responseMessage, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/deactivate-customer/{id}")
    public ResponseEntity<String> deactivateCustomer(@PathVariable String email)throws InterruptedException {
//        logger.info("AdminController :: Deactivate customer execution");
        String responseMessage=adminService.DeactivateCustomer(email);
        return new ResponseEntity<>(responseMessage, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/activate-seller/{id}")
    public ResponseEntity<String> activateSeller(@PathVariable String email)throws InterruptedException{
//        logger.info("AdminController :: Activate seller execution");
        String responseMessage= adminService.ActivateSeller(email);
        return new ResponseEntity<>(responseMessage,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/deactivate-seller/{id}")
    public ResponseEntity<String> deactivateSeller(@PathVariable String email)throws InterruptedException{
//        logger.info("AdminController :: Deactivate seller execution");
        String responseMessage = adminService.DeactivateSeller(email);
        return new ResponseEntity<>(responseMessage,HttpStatus.ACCEPTED);
    }

}
