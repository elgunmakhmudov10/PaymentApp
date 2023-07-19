package com.example.paymentsappingress.dto.ManagingUsers;

import lombok.Data;

@Data
public class UserPasswordResetDto {
    private String password;
    private String confirmPassword;
    private String mail;
    private String verificationEmail;
    private String verificationCode;

}
