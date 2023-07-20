package com.example.paymentsappingress.repository;

import com.example.paymentsappingress.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByMail(String email);

    User findByUserName(String userName);

    User findByMail(String email);



}
