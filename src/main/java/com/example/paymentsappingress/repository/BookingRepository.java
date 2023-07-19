package com.example.paymentsappingress.repository;

import com.example.paymentsappingress.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
