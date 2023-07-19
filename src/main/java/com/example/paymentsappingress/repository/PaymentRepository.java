package com.example.paymentsappingress.repository;

import com.example.paymentsappingress.model.Booking;
import com.example.paymentsappingress.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
