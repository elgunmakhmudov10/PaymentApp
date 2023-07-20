package com.example.paymentsappingress.dto.response;

import com.example.paymentsappingress.model.CourseType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data

@NoArgsConstructor
public class BookingResponse implements Serializable {
    public static final long serialVersionUID = 1234123456123L;

    Long id;

    String bookingDate;

    String totalCost;

    PaymentResponse payment;
    CourseType courseType;
    List<CourseType> courseTypeList;
}

