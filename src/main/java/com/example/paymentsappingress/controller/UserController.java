package com.example.paymentsappingress.controller;

import com.example.paymentsappingress.dto.ManagingUsers.UserLoginDto;
import com.example.paymentsappingress.dto.ManagingUsers.UserPasswordResetDto;
import com.example.paymentsappingress.dto.ManagingUsers.UserRegisterDto;
import com.example.paymentsappingress.dto.request.UserRequest;
import com.example.paymentsappingress.dto.response.UserResponse;
import com.example.paymentsappingress.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDto createUserDto) {
        if (userService.getOneUserByUserName(createUserDto.getUserName()) != null) {
            return new ResponseEntity<>("Username already in use!", HttpStatus.BAD_REQUEST);
        }
        if (userService.getOneUserByEmail(createUserDto.getMail()) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }
        if (!userService.validatePassword(createUserDto)) {
            return new ResponseEntity<>("Password is different from Confirm Password", HttpStatus.BAD_REQUEST);
        }
        userService.registerUser(createUserDto);
        return new ResponseEntity<>("You Are Successfully Registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userLoginDto) {
        if (userService.getOneUserByUserName(userLoginDto.getUserName()) == null) {
            return new ResponseEntity<>("Username not found", HttpStatus.NOT_FOUND);
        }
        if (!userService.loginUser(userLoginDto)) {
            return new ResponseEntity<>("Unauthorized Login", HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>("You Are Successfully Login", HttpStatus.OK);
    }

    @PutMapping("/resetPasswordByEmail")
    public ResponseEntity<String> resetPasswordByVerificationEmail(@RequestBody UserPasswordResetDto passwordResetDto) {
        if (!userService.validateVerificationEmail(passwordResetDto)) {
            return new ResponseEntity<>("Verification Email is incorrect", HttpStatus.NOT_FOUND);
        }
        if (!passwordResetDto.getPassword().equals(passwordResetDto.getConfirmPassword())) {
            return new ResponseEntity<>("Password is different from Confirm Password", HttpStatus.BAD_REQUEST);
        }
        userService.resetPassword(passwordResetDto);
        return new ResponseEntity<>("Password changed successfully",HttpStatus.OK);
    }

    @PutMapping("/resetPasswordByCode")
    public ResponseEntity<String> resetPasswordByVerificationCode(@RequestBody UserPasswordResetDto passwordResetDto) {
        if (!userService.validateVerificationCode(passwordResetDto)) {
            return new ResponseEntity<>("Verification Code is incorrect", HttpStatus.NOT_FOUND);
        }
        if (!passwordResetDto.getPassword().equals(passwordResetDto.getConfirmPassword())) {
            return new ResponseEntity<>("Password is different from Confirm Password", HttpStatus.BAD_REQUEST);
        }
        userService.resetPassword(passwordResetDto);
        return new ResponseEntity<>("Password changed successfully",HttpStatus.OK);
    }




    @GetMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Successfully registered");
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.save(userRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest,
                                               @PathVariable Long userId) {
        return new ResponseEntity<>(userService.update(userRequest, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);

    }
}




