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
    private final  UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterDto userDto) {

        userService.register(userDto);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto loginDto) {

        if (userService.login(loginDto)) {
            return new ResponseEntity<>("User login successfully", HttpStatus.CREATED);

        }

        return new ResponseEntity<>("Password or username is incorrect", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody UserPasswordResetDto resetPasswordDto, @RequestParam("email") String email) {
        userService.resetPassword(resetPasswordDto, email);

        return new ResponseEntity<>("Password reset successfully", HttpStatus.CREATED);
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




