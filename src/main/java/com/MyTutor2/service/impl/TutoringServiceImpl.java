package com.MyTutor2.service.impl;

import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.TutoringService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutoringServiceImpl implements TutoringService {

    private TutoringRepository tutoringRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);  //initialise a logger to log messages

    public TutoringServiceImpl(TutoringRepository tutoringRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.tutoringRepository = tutoringRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<TutorialViewDTO> findAllByCategoryID(Long id) {

        List<TutoringOffer> listOfOffers = tutoringRepository.findAllByCategoryId(id);

        return returnListOfOffersAsViewDTO(listOfOffers);
    }

    @Override
    public List<TutorialViewDTO> findAllTutoringOffersByUserId(Long id) {

        List<TutoringOffer> tutoringOffersLogedInUser = tutoringRepository.findAllByAddedById(id);

        List<TutorialViewDTO> listOfViewOffers = tutoringOffersLogedInUser.stream()
                .map(currentOffer-> {
                    TutorialViewDTO tutorialViewDTO = modelMapper.map(currentOffer, TutorialViewDTO.class);
                    tutorialViewDTO.setEmailOfTheTutor(currentOffer.getAddedBy().getEmail());
                    return tutorialViewDTO;

                }).toList();

        return listOfViewOffers;
    }

    @Override
    public void addTutoringOffer(TutorialAddDTO tutorialAddDTO, String userName) {

        TutoringOffer tutoringOffer = modelMapper.map(tutorialAddDTO, TutoringOffer.class);

        User user = userRepository.findByUsername(userName).get();

        tutoringOffer.setAddedBy(user);

        Category category = categoryRepository.findByName(tutorialAddDTO.getCategory());

        tutoringOffer.setCategory(category);

        tutoringRepository.save(tutoringOffer);

        LOGGER.info("A new tutoring offer was added by {}.", userName);

    }

    @Override
    public void removeOfferById(Long id) {
        tutoringRepository.deleteById(id);
    }

    private List<TutorialViewDTO> returnListOfOffersAsViewDTO(List<TutoringOffer> listOfOffers) {

//        List<TutorialViewDTO> outputListOfDTOs = new ArrayList<>();
//
//        for (TutoringOffer currentTutoringOffer : listOfOffers) {
//
//            TutorialViewDTO tutorialViewDTO = modelMapper.map(currentTutoringOffer, TutorialViewDTO.class);
//            tutorialViewDTO.setEmailOfTheTutor(currentTutoringOffer.getAddedBy().getEmail());
//            outputListOfDTOs.add(tutorialViewDTO);
//
//        }
//
//        return outputListOfDTOs;

        return listOfOffers.stream()
                .map(offer -> {
                    TutorialViewDTO dto = modelMapper.map(offer, TutorialViewDTO.class);
                    dto.setEmailOfTheTutor(offer.getAddedBy().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());

    }

}
