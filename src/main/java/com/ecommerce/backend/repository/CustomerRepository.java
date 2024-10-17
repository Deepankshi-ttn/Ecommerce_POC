package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Customer;
import com.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByUser(User user);
    //List<Customer> findAllCustomers(Pageable pageable);
    long count();

}