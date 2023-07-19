package com.example.paymentsappingress.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder

public class PaymentResponse implements Serializable {
    public static final long serialVersionUID = 1234123456123L;

    private Long id;

    private Double paymentAmount;

    private String paymentMethod;

    private String paymentDate;

}
