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
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void register(UserRegisterDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        if (!user.getPassword().equals(user.getRepeatPassword())) {
            throw new RuntimeException("Repeat Password Correctly");
        }

        if (userRepository.existsByMail(userDto.getMail())) {
            throw new RuntimeException("Email Already exists");
        }

        userRepository.save(user);

    }

    public boolean login(UserLoginDto loginDto) {
        User user = userRepository.findByUserName(loginDto.getUserName());

        if (user == null) {
            throw new RuntimeException("Not found username " + loginDto.getUserName());
        }

        return user.getPassword().equals(loginDto.getPassword());
    }

    public void resetPassword(UserPasswordResetDto resetPasswordDto, String email) {
        User user = userRepository.findByMail(resetPasswordDto.getMail());

        if (!user.getMail().equals(email)) {
            throw new RuntimeException("Invalid email");
        }

        user.setPassword(resetPasswordDto.getNewPassword());

        userRepository.save(user);
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

