package com.example.paymentsappingress.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingRequest {
    private String bookingDate;
    private Double totalCost;
}
