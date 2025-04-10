package com.MyTutor2.repo;

import com.MyTutor2.model.entity.TutoringOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutoringRepository extends JpaRepository<TutoringOffer, Long> {


    List<TutoringOffer> findAllByCategoryId(Long i);

    List<TutoringOffer> findAllByAddedById(Long id);


}
