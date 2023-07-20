package com.example.paymentsappingress.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PaymentResponse implements Serializable {
    public static final long serialVersionUID = 1234123456123L;

     Long id;

     String paymentAmount;
     String paymentMethod;

     String paymentDate;

}
