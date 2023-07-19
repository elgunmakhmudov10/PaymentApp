package com.example.paymentsappingress.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserRequest {
    private String name;
    private String userName;
    private String mail;
    private String phoneNumber;
}
