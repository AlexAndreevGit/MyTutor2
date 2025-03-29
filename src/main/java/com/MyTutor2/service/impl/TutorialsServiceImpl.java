package com.MyTutor2.service.impl;

import com.MyTutor2.model.DTOs.StackExchangeQuestionDTO;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.TutorialsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TutorialsServiceImpl implements TutorialsService {

    private TutoringRepository tutoringRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private final RestClient restClient;

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);  //initialise a logger to log messages

    public TutorialsServiceImpl(TutoringRepository tutoringRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, RestClient.Builder restClientBuilder) {
        this.tutoringRepository = tutoringRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.restClient = restClientBuilder.build();
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
    public List<StackExchangeQuestionDTO> getPythonQuestions() {

        String url = "https://api.stackexchange.com/2.3/search/advanced?order=desc&sort=activity&tagged=python&site=stackoverflow";

        var response = restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        List<StackExchangeQuestionDTO> questions = new ArrayList<>();

        for (Map<String, Object> item : items) {
            StackExchangeQuestionDTO question = new StackExchangeQuestionDTO();

            // Set title from the question title
            question.setTitle((String) item.get("title"));

            // Set url from the question link
            question.setUrl((String) item.get("link"));

            // Set view count
            question.setViewCount((Integer) item.get("view_count"));

            // Set answer count
            question.setAnswerCount((Integer) item.get("answer_count"));

            // Extract owner information for profile name and image
            if (item.containsKey("owner") && item.get("owner") != null) {
                Map<String, Object> owner = (Map<String, Object>) item.get("owner");

                // Set profile name
                if (owner.containsKey("display_name")) {
                    question.setProfileName((String) owner.get("display_name"));
                }

                // Set profile image URL
                if (owner.containsKey("profile_image")) {
                    question.setProfileImageUrl((String) owner.get("profile_image"));
                }
            }

            questions.add(question);
        }

        return questions;
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
