package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Seller;
import com.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller,Long> {


    Optional<Seller> findByUser(User user);

}