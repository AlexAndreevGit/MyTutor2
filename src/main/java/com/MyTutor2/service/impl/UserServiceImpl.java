package com.MyTutor2.service.impl;

import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.entity.UserRoleEntity;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.repo.UserRoleRepository;
import com.MyTutor2.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {

        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        UserRoleEntity userRoleEntity = userRoleRepository.findById(1L).get();
        List<UserRoleEntity> userRoles = new ArrayList<>();
        userRoles.add(userRoleEntity);
        user.setRoles(userRoles);

        userRepository.save(user);

        LOGGER.info("A new user with the name {} was created.",user.getName());


    }

}
