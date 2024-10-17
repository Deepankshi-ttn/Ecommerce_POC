package com.ecommerce.backend.service.impl.admin;

import com.ecommerce.backend.dto.request.CustomerDto;
import com.ecommerce.backend.dto.request.SellerDto;
import com.ecommerce.backend.entity.Customer;
import com.ecommerce.backend.entity.Seller;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptionHandling.UserNotFoundException;
import com.ecommerce.backend.repository.CustomerRepository;
import com.ecommerce.backend.repository.SellerRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.EmailService;
import com.ecommerce.backend.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    EmailService emailService;

    @Override
    public List<CustomerDto> customerDtoList(Pageable pageable, String email) {
        Page<Customer> customerList = customerRepository.findAll(pageable);
        //Admin Api -To getting all the customer details
        //Filtering on the basis of email
        if (email != null) {
            return userRepository.findByEmail(email).stream().map(e ->
            {
                Customer customer=customerRepository
                        .findByUser(e)
                        .orElseThrow(()->new UserNotFoundException("Customer not found"));
                CustomerDto customerDto = new CustomerDto();
//                customerDto.setId(e.getId());
                customerDto.setFirstName(e.getFirstName());
                customerDto.setMiddleName(e.getMiddleName());
                customerDto.setLastName(e.getLastName());
                customerDto.setEmail(e.getEmail());
                customerDto.setActive(e.getIsActive());
                customerDto.setContact(customer.getContact());
                return customerDto;
            }).collect(Collectors.toList());
        }

        return customerList.stream().map(e ->
        {

            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(e.getId());
            customerDto.setFirstName(e.getUser().getFirstName());
            customerDto.setMiddleName(e.getUser().getMiddleName());
            customerDto.setLastName(e.getUser().getLastName());
            customerDto.setEmail(e.getUser().getEmail());
            customerDto.setActive(e.getUser().getIsActive());
            customerDto.setContact(e.getContact());
            return customerDto;
        }).collect(Collectors.toList());
    }
    @Override
    public List<SellerDto> sellerDtoList(Pageable pageable, String email) {

        Page<Seller> sellerList = sellerRepository.findAll(pageable);
        if (email != null) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
            Seller seller = sellerRepository.findByUser(user).orElseThrow(() -> new UserNotFoundException("No seller found"));
            return List.of(user).stream().map(e ->
            {
                SellerDto sellerDto = new SellerDto();
//                sellerDto.setId(e.getId());
                sellerDto.setFirstName(e.getFirstName());
                sellerDto.setMiddleName(e.getMiddleName());
                sellerDto.setLastName(e.getLastName());
                sellerDto.setCompanyName(seller.getCompanyName());
                sellerDto.setEmail(e.getEmail());
                sellerDto.setCompanyContact(seller.getCompanyContact());
                sellerDto.setAddress(e.getAddress().get(0));
                sellerDto.setActive(e.getIsActive());
                return sellerDto;
            }).collect(Collectors.toList());
        }

        return sellerList.stream().map(e ->
        {
            SellerDto sellerDto = new SellerDto();
            sellerDto.setId(e.getId());
            sellerDto.setFirstName(e.getUser().getFirstName());
            sellerDto.setMiddleName(e.getUser().getMiddleName());
            sellerDto.setLastName(e.getUser().getLastName());
            sellerDto.setCompanyName(e.getCompanyName());
            sellerDto.setCompanyContact(e.getCompanyContact());
            sellerDto.setEmail(e.getUser().getEmail());
            sellerDto.setActive(e.getUser().getIsActive());
            sellerDto.setAddress(e.getUser().getAddress().get(0));
            return sellerDto;
        }).collect(Collectors.toList());
    }


    public String ActivateCustomer(String email) throws MailException,InterruptedException{
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException("User is not present")
        );
        if (!user.getIsActive())
        {
            user.setIsActive(true);
            userRepository.save(user);
            emailService.customEmail(user,"Customer Activated","Congratulations! your account is activated!");
            return "Customer account is now activated";
        }
        return "Customer account is already activated";
    }

    public String DeactivateCustomer(String email) throws MailException,InterruptedException{
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new UserNotFoundException("User is not present")
        );
        if (user.getIsActive())
        {
            user.setIsActive(false);
            userRepository.save(user);
            emailService.customEmail(user,"Customer Deactivated","Your account is now deactivated");
            return "Customer account is now deactivated";
        }
        return "Customer account is already deactivated";
    }

    public String ActivateSeller(String email)throws MailException,InterruptedException{
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new UserNotFoundException("User is not present")
        );
        if (!user.getIsActive())
        {
            user.setIsActive(true);
            userRepository.save(user);
            emailService.customEmail(user,"Seller Activated","Congratulations! your account is activated");
            return "Seller account is now activated";
        }
        return "Seller account is already activated";
    }

    public String DeactivateSeller(String email)throws MailException,InterruptedException{
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new UserNotFoundException("User is not present")
        );
        if (user.getIsActive())
        {
            user.setIsActive(false);
            userRepository.save(user);
            emailService.customEmail(user,"Seller Deactivated!","Your account is deactivated");
            return "Seller account is now deactivated";
        }
        return "Seller account is already deactivated";
    }
}
