package com.ecommerce.backend.service.impl.register;

import com.ecommerce.backend.dto.request.SellerDetailsDto;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.Seller;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptionHandling.EmailAlreadyExistsException;
import com.ecommerce.backend.exceptionHandling.EmailVerificationFailedException;
import com.ecommerce.backend.exceptionHandling.PasswordMatchException;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.EmailService;
import com.ecommerce.backend.service.register.RegisterSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RegisterSellerServiceImpl implements RegisterSellerService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public void createSeller(SellerDetailsDto sellerDetails) {
        if (emailAlreadyExistsCheck(sellerDetails.getEmail())) {
            throw new EmailAlreadyExistsException("Email Already exists: " + sellerDetails.getEmail());
        }

        if (matchPassword(sellerDetails.getPassword(), sellerDetails.getConfirmPassword())) {
            //  logger.error("RegisterService :: password and confirm password does not match");
            throw new PasswordMatchException("Password and confirm password do not match");
        }

//        User user = new User();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Role role = roleRepository.findByAuthority("ROLE_SELLER")
                .orElseThrow(() -> new RuntimeException("Role 'ROLE_SELLER' not found"));
        User user = User.builder()
                .role(role)
                .email(sellerDetails.getEmail())
                .firstName(sellerDetails.getFirstName())
                .middleName(sellerDetails.getMiddleName())
                .lastName(sellerDetails.getLastName())
                .password(sellerDetails.getPassword())
                .passwordUpdatedDate(LocalDateTime.now())
                .createdBy(role.getAuthority())
                .modifiedBy(role.getAuthority())
                .modifiedOn(LocalDateTime.now())
                .createdOn(LocalDateTime.now())
                .isActive(false)
                .invalidAttemptCount(0)
                .build();
//        user.setRole(role);
//        user.setEmail(sellerDetails.getEmail());
//        user.setFirstName(sellerDetails.getFirstName());
//        user.setMiddleName(sellerDetails.getMiddleName());
//        user.setLastName(sellerDetails.getLastName());
//        user.setPassword(sellerDetails.getPassword());
//        user.setPasswordUpdatedDate(LocalDateTime.now());
//        user.setCreatedBy(role.getAuthority());
//        user.setModifiedBy(role.getAuthority());
//        user.setModifiedOn(LocalDateTime.now());
//        user.setCreatedOn(LocalDateTime.now());
//        user.setInvalidAttemptCount(0);

        // Building the Seller object using Builder pattern
        Seller seller = Seller.builder()
                .gst(sellerDetails.getGst())
                .companyContact(sellerDetails.getCompanyContact())
                .companyName(sellerDetails.getCompanyName())
                .user(user) // Set the user object here
                .build();

//        Seller seller = new Seller();
//        seller.setGst(sellerDetails.getGst());
//        seller.setCompanyContact(sellerDetails.getCompanyContact());
//        seller.setCompanyName(sellerDetails.getCompanyName());
        // Building the Address object using Builder pattern
        Address address = Address.builder()
                .addressLine(sellerDetails.getAddressLine())
                .city(sellerDetails.getCity())
                .country(sellerDetails.getCountry())
                .label(sellerDetails.getLabel())
                .state(sellerDetails.getState())
                .zipCode(sellerDetails.getZipCode())
                .build();
//        Address address = new Address();
//        address.setAddressLine(sellerDetails.getAddressLine());
//        address.setCity(sellerDetails.getCity());
//        address.setCountry(sellerDetails.getCountry());
//        address.setLabel(sellerDetails.getLabel());
//        address.setState(sellerDetails.getState());
//        address.setZipCode(sellerDetails.getZipCode());

        user.setAddress(List.of(address));
        seller.setUser(user);

        sellerRepository.save(seller);

        try {
            emailService.SellerVerificationEmail(user);
        } catch (Exception e) {
//            logger.error("RegisterService :: "+e.getMessage());
            throw new EmailVerificationFailedException(e.getMessage());
        }
    }



    public void sendEmail(User user) {
        try {
            emailService.verificationEmail(user);
        } catch (Exception e) {
            // logger.error("Register Service ::" +e.getMessage());
        }
    }

    public boolean emailAlreadyExistsCheck(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean matchPassword(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

}
