package com.MyTutor2.repo;

import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutoringRepository extends JpaRepository<TutoringOffer,Long> {

    //TODO find all by category id

    List<TutoringOffer> findAllByCategoryId(Long i);

    //TODO find by added by id


}
