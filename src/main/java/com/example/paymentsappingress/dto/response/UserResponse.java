package com.example.paymentsappingress.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse  implements Serializable {
    private long id;
    private String name;
    private String userName;
    private String mail;
    private String phoneNumber;
    private List<BookingResponse> bookings;
}
