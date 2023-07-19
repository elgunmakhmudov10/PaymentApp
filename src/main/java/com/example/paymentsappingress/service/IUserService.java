package com.example.paymentsappingress.service;

import com.example.paymentsappingress.dto.ManagingUsers.UserLoginDto;
import com.example.paymentsappingress.dto.ManagingUsers.UserPasswordResetDto;
import com.example.paymentsappingress.dto.ManagingUsers.UserRegisterDto;
import com.example.paymentsappingress.model.User;

public interface IUserService {
    public void registerUser(UserRegisterDto UserRegisterDto);
    public boolean loginUser(UserLoginDto userLoginDto);
    public void resetPassword(UserPasswordResetDto passwordResetDto);
    public User getOneUserByUserName(String username);
    public User getOneUserByEmail(String email);
    public boolean validatePassword(UserRegisterDto UserRegisterDto);
    public boolean validateVerificationEmail(UserPasswordResetDto passwordResetDto);
    public boolean validateVerificationCode(UserPasswordResetDto passwordResetDto);


}
