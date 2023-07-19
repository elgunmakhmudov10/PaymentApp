package com.example.paymentsappingress.dto.response;

import com.example.paymentsappingress.model.CourseType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class BookingResponse implements Serializable {
    public static final long serialVersionUID = 1234123456123L;

    private Long id;

    private String bookingDate;

    private Double totalCost;

    private PaymentResponse payment;
    private List<CourseType> courseTypeList;
}

