package com.MyTutor2.service;

import com.MyTutor2.model.DTOs.StackExchangeQuestionDTO;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;

import java.util.List;

public interface TutorialsService {

    List<TutorialViewDTO> findAllByCategoryID(Long id);

    List<TutorialViewDTO> findAllTutoringOffersByUserId(Long id);

    List<StackExchangeQuestionDTO> getPythonQuestions();

    void addTutoringOffer(TutorialAddDTO tutorialAddDTO, String userName);

    void removeOfferById(Long id);
}
