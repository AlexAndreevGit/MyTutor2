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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutoringServiceImpl implements TutoringService {

    private TutoringRepository tutoringRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public TutoringServiceImpl(TutoringRepository tutoringRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.tutoringRepository = tutoringRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<TutorialViewDTO> findAllByCategoryID(Long id) {

        List<TutoringOffer> listOfOffers = tutoringRepository.findAllByCategoryId(id);

        List<TutorialViewDTO> listOfOffersAsDTO = returnListOfOffersAsViewDTO(listOfOffers);
        //TODO remove redundant code

        return listOfOffersAsDTO;
    }

    @Override
    public void addTutoringOffer(TutorialAddDTO tutorialAddDTO, String userName) {

        TutoringOffer tutoringOffer = modelMapper.map(tutorialAddDTO,TutoringOffer.class);

        User user =  userRepository.findByUsername(userName).get();

        tutoringOffer.setAddedBy(user);

        Category category = categoryRepository.findByName(tutorialAddDTO.getCategory());

        tutoringOffer.setCategory(category);

        tutoringRepository.save(tutoringOffer);

    }

    private List<TutorialViewDTO> returnListOfOffersAsViewDTO(List<TutoringOffer> listOfOffers) {

        List<TutorialViewDTO> outputListOfDTOs = new ArrayList<>();

        //TODO solve this with a map
        for(TutoringOffer currentTutoringOffer : listOfOffers){

            TutorialViewDTO tutorialViewDTO =  modelMapper.map(currentTutoringOffer,TutorialViewDTO.class);
            tutorialViewDTO.setEmailOfTheTutor(currentTutoringOffer.getAddedBy().getEmail());
            outputListOfDTOs.add(tutorialViewDTO);

        }

        return outputListOfDTOs;

    }

}
