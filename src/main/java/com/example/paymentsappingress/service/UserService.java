package com.example.paymentsappingress.service;

import com.example.paymentsappingress.dto.ManagingUsers.UserLoginDto;
import com.example.paymentsappingress.dto.ManagingUsers.UserPasswordResetDto;
import com.example.paymentsappingress.dto.ManagingUsers.UserRegisterDto;
import com.example.paymentsappingress.dto.request.UserRequest;
import com.example.paymentsappingress.dto.response.UserResponse;
import com.example.paymentsappingress.model.User;
import com.example.paymentsappingress.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void registerUser(UserRegisterDto UserRegisterDto) {
        User user = modelMapper.map(UserRegisterDto, User.class);
        userRepository.save(user);
    }

    @Override
    public boolean loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserName(userLoginDto.getUserName());
        if (user.getPassword().equals(userLoginDto.getPassword())) {
            return true;
        } else return false;
    }

    @Override
    public void resetPassword(UserPasswordResetDto passwordResetDto) {
        User user = userRepository.findByMail(passwordResetDto.getMail());
        user.setPassword(passwordResetDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public User getOneUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public User getOneUserByEmail(String email) {
        return userRepository.findByMail(email);
    }

    @Override
    public boolean validatePassword(UserRegisterDto UserRegisterDto) {
        if (UserRegisterDto.getPassword().equals(UserRegisterDto.getConfirmPassword())) {
            return true;
        } else return false;
    }

    @Override
    public boolean validateVerificationEmail(UserPasswordResetDto passwordResetDto) {
        User user = userRepository.findByMail(passwordResetDto.getMail());
        if (user.getVerificationEmail().equals(passwordResetDto.getVerificationEmail())) {
            return true;
        } else return false;
    }

    @Override
    public boolean validateVerificationCode(UserPasswordResetDto passwordResetDto) {
        User user = userRepository.findByMail(passwordResetDto.getMail());
        if (user.getVerificationCode().equals(passwordResetDto.getVerificationCode())) {
            return true;
        } else return false;
    }


    public void register(UserRequest dto) {
    }

    @Cacheable(cacheNames = "users", key = "#root.methodName")
    public List<UserResponse> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#userId", cacheNames = "user")
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(
                String.format("User not found by id -%s", userId)
        ));
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse save(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        return modelMapper.map(userRepository.save(user), UserResponse.class);

    }

    @Cacheable(cacheNames = "users", key = "#userId")
    public UserResponse update(UserRequest userRequest, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException(
                String.format("User not found by id -%s", userId)
        ));
        User responseUser = modelMapper.map(userRequest, User.class);
        responseUser.setId(userId);
        return modelMapper.map(userRepository.save(responseUser), UserResponse.class);
    }

    @CacheEvict(cacheNames = "users", key = "#userId")
    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(
                String.format("User not found by id -%s", userId)
        ));
        userRepository.delete(user);
    }


}

