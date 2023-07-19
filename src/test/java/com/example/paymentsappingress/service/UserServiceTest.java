package com.example.paymentsappingress.service;

import com.example.paymentsappingress.dto.request.UserRequest;
import com.example.paymentsappingress.dto.response.UserResponse;
import com.example.paymentsappingress.model.User;
import com.example.paymentsappingress.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    void givenAllUserWhenGetSuccess() {
        //Arrange

        List<User> userList = new ArrayList<>();

        User mockUser = User.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        userList.add(mockUser);

        when(userRepository.findAll()).thenReturn(userList);


        List<UserResponse> mockUserResponseList = new ArrayList<>();

        UserResponse mockUserResponse = UserResponse.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        mockUserResponseList.add(mockUserResponse);


        when(modelMapper.map(userList.get(0), UserResponse.class)).thenReturn(mockUserResponseList.get(0));


        //Act
        List<UserResponse> userResponseList = userService.findAll();

        //Assert
        assertEquals(mockUserResponseList, userResponseList);
    }

    @Test
    void givenValidIdWhenGetUserThenSuccess() {
        //Arrange
        long id = 1L;


        User mockUser = User.builder()
                .id(id)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

        UserResponse mockUserResponse = UserResponse.builder()
                .id(id)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(modelMapper.map(mockUser, UserResponse.class)).thenReturn(mockUserResponse);


        //Act
        UserResponse userResponse = userService.findById(1L);

        //Assert
        assertEquals(userResponse.getId(), mockUserResponse.getId());
    }

    @Test
    void givenInValidIdWhenGetUserThenException() {
        //Arrange
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Act & assert
        assertThatThrownBy(() -> userService.findById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found by id -%s", id);

    }

    @Test
    void saveUserWhenSuccess() {
        //Arrange
        User mockUser = User.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(userRepository.save(any())).thenReturn(mockUser);

        UserRequest userRequestDto = UserRequest.builder()
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(modelMapper.map(userRequestDto, User.class)).thenReturn(mockUser);

        UserResponse mockUserResponse = UserResponse.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(modelMapper.map(mockUser, UserResponse.class)).thenReturn(mockUserResponse);


        //Act
        UserResponse userResponse = userService.save(userRequestDto);


        //Assert
        assertEquals(mockUserResponse, userResponse);
    }

    @Test
    void updateUserWhenSuccess() {
        //Arrange
        User mockUser = User.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        UserRequest userRequestDto = UserRequest.builder()
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(modelMapper.map(any(UserRequest.class), eq(User.class)))
                .thenReturn(mockUser);

        User updatedUser = User.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponse mockUserResponse = UserResponse.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunamhmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(modelMapper.map(any(User.class), eq(UserResponse.class)))
                .thenReturn(mockUserResponse);

        //Act
        userService.update(userRequestDto, 1L);

        //Assert
        assertThat(mockUserResponse.getId()).isEqualTo(1L);
        assertThat(mockUserResponse.getName()).isEqualTo("Elgun");
    }

    @Test
    void givenInvalidWhenUpdateUserThenNotFound() {
        //Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserRequest userRequestDto = UserRequest.builder()
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        //Act&Assert
        assertThatThrownBy(() -> userService.update(userRequestDto, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found by id -1");
    }

    @Test
    void deleteUserWhenSuccess() {
        // Arrange

        User mockUser = User.builder()
                .id(1L)
                .name("Elgun")
                .userName("Mahmudov")
                .mail("elgunmahmudov04@mail.ru")
                .phoneNumber("0105500010")
                .build();

        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));


        // Act
        userService.delete(mockUser.getId());

        // Assert
        verify(userRepository, times(1)).delete(mockUser);
    }

    @Test
    void givenInvalidWhenDeleteUserIsFail() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act&Assert
        assertThatThrownBy(() -> userService.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found by id -1");
    }
}
