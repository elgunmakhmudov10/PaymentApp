package com.example.paymentsappingress.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class PaymentRequest {

    private Double paymentAmount;

    private String paymentMethod;

    private String paymentDate;

}
