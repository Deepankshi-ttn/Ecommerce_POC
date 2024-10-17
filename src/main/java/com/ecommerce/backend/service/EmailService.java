package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.request.EmailDto;
import com.ecommerce.backend.entity.EmailVerificationToken;
import com.ecommerce.backend.entity.ForgotPasswordVerificationToken;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.EmailVerificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    @Autowired
    EmailVerificationRepository emailVerificationRepository;

    @Value("${email.verification.url}")
    private String url;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${sender.email}")
    private String senderEmail;

    @Async
    public void sendNotification(User user) throws MailException, InterruptedException {

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user);
        emailVerificationRepository.save(emailVerificationToken);

        System.out.println("Sleeping now...");
        Thread.sleep(10000);

        System.out.println("Sending email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject("Activate the Gmail");
        mail.setText("Click this Link For Activation?");
        mail.setText(url+"/"+emailVerificationToken.getToken());
        javaMailSender.send(mail);
        logger.info("EmailService :: Email Sent!");

    }
    @Async
    public void verificationEmail(User user) throws MailException, InterruptedException {

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user);
        emailVerificationRepository.save(emailVerificationToken);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject("Activate the Gmail");
        mail.setText("Click this Link For Activation");
        mail.setText(url+"/"+emailVerificationToken.getToken());
        javaMailSender.send(mail);

        logger.info("EmailService :: Email Sent!");
    }

    @Async
    public void SellerVerificationEmail(User user) throws MailException {

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user);
        emailVerificationRepository.save(emailVerificationToken);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject("Activate the Gmail for Seller");
        mail.setText("Click this Link For Activation");
        mail.setText(url+"/"+emailVerificationToken.getToken());
        javaMailSender.send(mail);

        logger.info("EmailService :: Email Sent!");
    }

    @Async
    public void sendForgotPasswordEmail (EmailDto email, ForgotPasswordVerificationToken forgotPasswordVerificationToken) throws MailException, InterruptedException
    {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject("Verify the password");
        mail.setText("Click this Link For Verification");
        mail.setText(forgotPasswordVerificationToken.getToken());
        javaMailSender.send(mail);
        logger.info("EmailService :: Email Sent!");
    }
    @Async
    public void sendResetPasswordEmail (User user) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject("Password Reset");
        mail.setText("Password has been reset successfully!");
        javaMailSender.send(mail);
        logger.info("EmailService :: Email Sent!");
    }

    @Async
    public void registrationVerifiedEmail (User user) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject("Successful Registration");
        mail.setText("Kudos!. You have been successfully registered.");
        javaMailSender.send(mail);
        logger.info("EmailService :: Email Sent!");
    }

    @Async
    public void customEmail (User user,String subject,String body) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(adminEmail);
        mail.setSubject(subject);
        mail.setText(body);
        javaMailSender.send(mail);
        logger.info("EmailService :: Email Sent!");
    }
    @Async
    public void sendListOfNotActivateSeller(List<String> seller) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(adminEmail);
        mail.setSubject("Pending!! to Activate Seller!");
        mail.setFrom(senderEmail);
        mail.setText("Dear Admin,\n Following user are left to verify.");

        javaMailSender.send(mail);

        logger.debug("Email Sent for not active list of seller");
    }
    @Async
    public void sendListOfNotActivateProducts(List<String> products) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(adminEmail);
        mail.setSubject("Pending!! to Activate Products!");
        mail.setFrom(senderEmail);
        mail.setText("Dear Admin,\n Following products are left to verify:\n");
        String res = "";
        for(int i = 0; i<products.size(); i++)
        {
            res +=i+1+". "+ products+"\n";
        }
        mail.setText("Dear Admin,\n Following products are left to verify.\n"+res);

        javaMailSender.send(mail);

        logger.debug("Email Sent for non-active list of products");
    }
}

