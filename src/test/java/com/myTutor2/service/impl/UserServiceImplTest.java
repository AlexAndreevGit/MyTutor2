package com.MyTutor2.service.impl;

import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.entity.UserRoleEntity;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.repo.UserRoleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl mockUserService;  //inject mocked dependencies into the class being tested

    private UserRegisterDTO mockUserRegisterDTO;
    private User user;
    private UserRoleEntity userRoleEntity;

    @BeforeEach
    void setUp() {
        mockUserRegisterDTO = new UserRegisterDTO();
        mockUserRegisterDTO.setUsername("testUser");
        mockUserRegisterDTO.setPassword("password123");

        user = new User();
        user.setName("testUser");

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId(1L);

        when(modelMapper.map(mockUserRegisterDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode(mockUserRegisterDTO.getPassword())).thenReturn("encodedPassword");
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRoleEntity));
    }

    @Test
    void registerUser_ShouldSaveUser() {

        mockUserService.registerUser(mockUserRegisterDTO);

        //the verify() method is used to check if a specific method was called(specific number of times) on a mocked object.
        //was the .save() method called one time on the mockUserRepository with an User object ?
        verify(mockUserRepository, times(1)).save(any(User.class));

    }

//    @Override
//    public void registerUser(UserRegisterDTO userRegisterDTO) {
//
//        User user = modelMapper.map(userRegisterDTO, User.class);
//        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//
//        UserRoleEntity userRoleEntity = userRoleRepository.findById(1L).get();
//        List<UserRoleEntity> userRoles = new ArrayList<>();
//        userRoles.add(userRoleEntity);
//        user.setRoles(userRoles);
//
//        userRepository.save(user);
//
//        LOGGER.info("A new user with the name {} was created.",user.getName());
//
//    }

}