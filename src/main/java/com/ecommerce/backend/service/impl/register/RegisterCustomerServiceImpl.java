package com.ecommerce.backend.service.impl.register;

import com.ecommerce.backend.dto.request.CustomerDetailsDto;
import com.ecommerce.backend.entity.Customer;
import com.ecommerce.backend.entity.EmailVerificationToken;
import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.exceptionHandling.*;
import com.ecommerce.backend.repository.CustomerRepository;
import com.ecommerce.backend.repository.EmailVerificationRepository;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.EmailService;
import com.ecommerce.backend.service.register.RegisterCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class RegisterCustomerServiceImpl implements RegisterCustomerService {



    @Autowired
    private EmailVerificationRepository emailVerificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomerRepository customerRepository;;

    @Override
    public void verify(String token) {
        EmailVerificationToken emailVerificationToken = emailVerificationRepository
                .findByToken(token)
                .orElseThrow(() -> new TokenExpired("This token is invalid"));

        if (emailVerificationToken.isExpired()) {
            throw new TokenExpired("This token is invalid");
        }

        User user = emailVerificationToken.getUser();

        user.setIsActive(true);

        userRepository.save(user);

        try {
            emailService.registrationVerifiedEmail(user);
        } catch (InterruptedException e) {
           // logger.error("RegisterService :: "+e.getMessage());
            throw new RuntimeException(e);
        }
        emailVerificationRepository.delete(emailVerificationToken);
    }

    @Override
    public void resend_verify(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email is not present"));

        if (user.getIsActive()) {
            throw new UserIsAlreadyActive("User is already active!");
        }

        EmailVerificationToken emailVerificationToken = emailVerificationRepository
                .findByUser(user).orElseThrow(() -> new UserNotFoundException("User is not present"));

        emailVerificationRepository.delete(emailVerificationToken);

        sendEmail(user);

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

    //Create customer
    @Override
    public void createCustomer(CustomerDetailsDto customerDetails) {
        if(emailAlreadyExistsCheck(customerDetails.getEmail())){
            throw new EmailAlreadyExistsException("Email Already exists ::"+ customerDetails.getEmail());
        }

        if (matchPassword(customerDetails.getPassword(), customerDetails.getConfirmPassword())) {
          //  logger.error("RegisterService :: password and confirm password does not match");
            throw new PasswordMatchException("password and confirm password does not match");
        }
//        User user = new User();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Role role = roleRepository.findByAuthority("ROLE_CUSTOMER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setAuthority("ROLE_CUSTOMER");
                    newRole.setIsDeleted(false);
                    return roleRepository.save(newRole);  // Save and return the new role
                });

        //        System.out.println(role.toString());

        User user = User.builder()
                .role(role)
                .email(customerDetails.getEmail())
                .firstName(customerDetails.getFirstName())
                .middleName(customerDetails.getMiddleName())
                .lastName(customerDetails.getLastName())
                .password(customerDetails.getPassword())
                .passwordUpdatedDate(LocalDateTime.now())
                .createdBy(role.getAuthority())
                .modifiedBy(role.getAuthority())
                .modifiedOn(LocalDateTime.now())
                .createdOn(LocalDateTime.now())
                .isActive(false)
                .invalidAttemptCount(0)
                .build();
//        user.setRole(role);

//        //if username is unique
//        user.setEmail(customerDetails.getEmail());
//        user.setFirstName(customerDetails.getFirstName());
//        //Optional
//        user.setMiddleName();
//        user.setLastName();
//        user.setPassword("123");//bCryptPasswordEncoder.encode(customerDetails.getPassword()));
//        user.setPasswordUpdatedDate();
//        //LocalDateTime
//        user.setCreatedBy();
//        user.setModifiedBy();
//        user.setModifiedOn();
//        user.setCreatedOn();
//        user.setInvalidAttemptCount(0);

//        Customer customer = new Customer(null, user, customerDetails.getContact());
//        customer.setContact();
        Customer customer = Customer.builder()
                .contact(customerDetails.getContact())
                .user(user)
                .build();

        customerRepository.save(customer);
        try {
            emailService.verificationEmail(user);
        }catch(Exception e ){
           // logger.error("RegisterService :: "+e.getMessage());
            throw new EmailAlreadyExistsException(e.getMessage());
        }
        System.out.println(user);

    }

}
