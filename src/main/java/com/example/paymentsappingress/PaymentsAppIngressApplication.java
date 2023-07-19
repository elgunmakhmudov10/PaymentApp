package com.example.paymentsappingress;

import com.example.paymentsappingress.model.User;
import com.example.paymentsappingress.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentsAppIngressApplication implements CommandLineRunner {
    private final  UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(PaymentsAppIngressApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }




}
