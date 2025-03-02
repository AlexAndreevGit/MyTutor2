package com.MyTutor2.service;

import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;

import java.util.List;

public interface TutoringService {

    List<TutorialViewDTO> findAllByCategoryID(Long id);

    List<TutorialViewDTO> findAllTutoringOffersByUserId(Long id);

    void addTutoringOffer(TutorialAddDTO tutorialAddDTO, String userName);

    void removeOfferById(Long id);
}
