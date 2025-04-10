package com.myTutor2.service.impl;


import com.MyTutor2.exceptions.CategoryNotFoundException;
import com.MyTutor2.exceptions.UserNotFoundException;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.enums.CategoryNameEnum;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.impl.TutorialsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TutorialsServiceImplTest {

    @Mock
    private TutoringRepository tutoringRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TutorialsServiceImpl tutoringService;

    private TutorialAddDTO tutorialAddDTO;
    private User user;
    private Category category;
    private TutoringOffer tutoringOffer;
    private TutorialViewDTO tutorialViewDTO;

    @BeforeEach
    void setUp() {
        tutorialAddDTO = new TutorialAddDTO();
        tutorialAddDTO.setCategory(CategoryNameEnum.MATHEMATICS);
        //tutorialAddDTO.setName("Algebra Basics");
        //tutorialAddDTO.setDescription("Learn the basics of Algebra.");
        //tutorialAddDTO.setPrice(30.0);

        user = new User();
        user.setUsername("testUser");
        user.setEmail("testUser@example.com");

        category = new Category();
        //category.setName(CategoryNameEnum.MATHEMATICS);

        tutoringOffer = new TutoringOffer();
        tutoringOffer.setAddedBy(user);

        tutorialViewDTO = new TutorialViewDTO();
        tutorialViewDTO.setEmailOfTheTutor(user.getEmail());

    }


    @Test
    void testAddTutoringOffer() throws UserNotFoundException, CategoryNotFoundException {

        //----- Preparation -----
        //        TutoringOffer tutoringOffer = modelMapper.map(tutorialAddDTO, TutoringOffer.class);
        when(modelMapper.map(tutorialAddDTO, TutoringOffer.class)).thenReturn(tutoringOffer);

        //        User user = userRepository.findByUsername(userName).get();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        //        Category category = categoryRepository.findByName(tutorialAddDTO.getCategory());
        when(categoryRepository.findByName(CategoryNameEnum.MATHEMATICS)).thenReturn(category);


        //----- Call method -----
        tutoringService.addTutoringOffer(tutorialAddDTO, "testUser");

        //----- verification -----        tutoringRepository.save(tutoringOffer);   ->
        verify(tutoringRepository, times(1)).save(tutoringOffer);
    }


    @Test
    void findAllTutoringOffersByUserIdTest() {

        List<TutoringOffer> offer = new ArrayList<>();
        offer.add(tutoringOffer);

        when(tutoringRepository.findAllByAddedById(1L)).thenReturn(offer);
        when(modelMapper.map(tutoringOffer, TutorialViewDTO.class)).thenReturn(tutorialViewDTO);

        List<TutorialViewDTO> result = tutoringService.findAllTutoringOffersByUserId(1L);

        assertEquals(1,result.size());
        assertEquals("testUser@example.com",result.get(0).getEmailOfTheTutor());

    }





}

