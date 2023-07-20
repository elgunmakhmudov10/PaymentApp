package com.example.paymentsappingress.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentRequest {

    String paymentAmount;

    String paymentMethod;

     String paymentDate;

}
