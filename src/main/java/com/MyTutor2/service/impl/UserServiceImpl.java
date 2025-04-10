package com.MyTutor2.service.impl;

import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.entity.UserRoleEntity;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.repo.UserRoleRepository;
import com.MyTutor2.service.TutorialsService;
import com.MyTutor2.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @PersistenceContext //is used to mark the EntytyManager object for dependency injection
    private EntityManager entityManager; //is used to interact with the persistence context


    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;
    private TutorialsService tutorialsService;
    private TutoringRepository tutoringRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           TutorialsService tutorialsService, EntityManager entityManager, TutoringRepository tutoringRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.tutorialsService = tutorialsService;
        this.entityManager = entityManager;
        this.tutoringRepository = tutoringRepository;
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

    @Transactional //is used to specify the scope of a single database transaction
    @Override
    public void deleteUser(User logedInUser) throws TutorialNotFoundException {

        //delete all tutoring offers associated with this user
        List<TutorialViewDTO> submittedByMeTutorialsAsView = tutorialsService.findAllTutoringOffersByUserId(logedInUser.getId());

        //delete all offers associated with this user
        for (TutorialViewDTO tutorialViewDTO : submittedByMeTutorialsAsView) {
            tutorialsService.removeOfferById(tutorialViewDTO.getId());
        }


        entityManager.createNativeQuery("DELETE FROM user_roles WHERE user_id = ?")
                .setParameter(1, logedInUser.getId())
                .executeUpdate();

        logedInUser.getRoles().clear();

        userRepository.delete(logedInUser);
        System.out.println();


    }

}
