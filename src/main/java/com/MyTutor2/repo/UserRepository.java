package com.MyTutor2.repo;


import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //TODO FindByUsername and password
    //User findByUsername(String name);
    Optional<User> findByUsername(String username);


    //TODO findByUserName


}
