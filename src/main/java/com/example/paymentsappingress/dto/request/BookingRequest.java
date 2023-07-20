package com.example.paymentsappingress.dto.request;

import com.example.paymentsappingress.dto.response.PaymentResponse;
import com.example.paymentsappingress.model.CourseType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

@NoArgsConstructor
public class BookingRequest {
     String bookingDate;
     String totalCost;
     CourseType courseType;
     List<CourseType> courseTypeList;
}
