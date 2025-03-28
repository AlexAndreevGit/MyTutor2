package com.MyTutor2.controller;

import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class StatisticsController {

    private TutoringRepository tutoringRepository;
    private UserRepository userRepository;


    public StatisticsController(TutoringRepository tutoringRepository, UserRepository userRepository) {
        this.tutoringRepository = tutoringRepository;
        this.userRepository = userRepository;
    }


    @PreAuthorize("hasRole('ADMIN')") // SpringSecurity_12 Only users with the ADMIN role can access this method
    @GetMapping("/statistics")
    public String statistics(Model model) {


        List<TutoringOffer> countInformaticsTutorials = tutoringRepository.findAllByCategoryId(2L);

        List<TutoringOffer> countMathematicsTutorials = tutoringRepository.findAllByCategoryId(1L);

        List<TutoringOffer> countDatascienceTutorials = tutoringRepository.findAllByCategoryId(3L);

        List<User> countAllUsers= userRepository.findAll();


        model.addAttribute("countInformaticsTutorials", countInformaticsTutorials.size());

        model.addAttribute("countMathematicsTutorials", countMathematicsTutorials.size());

        model.addAttribute("countDatascienceTutorials", countDatascienceTutorials.size());

        model.addAttribute("countAllUsers", countAllUsers.size()-1); // minus 1 because we don't count the admin as a user


        return "statistics";
    }

}
