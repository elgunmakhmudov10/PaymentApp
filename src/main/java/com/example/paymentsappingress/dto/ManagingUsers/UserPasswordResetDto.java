package com.example.paymentsappingress.dto.ManagingUsers;

import lombok.Data;

@Data
public class UserPasswordResetDto {
    private String newPassword;
    private String confirmNewPassword;
    private String mail;
    private String verificationEmail;
    private String verificationCode;

}
