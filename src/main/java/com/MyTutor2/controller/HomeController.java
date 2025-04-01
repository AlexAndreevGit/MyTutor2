package com.MyTutor2.controller;

import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {


    private TutoringRepository tutoringRepository;
    private UserRepository userRepository;

    //constructor injection
    public HomeController(TutoringRepository tutoringRepository, UserRepository userRepository) {
        this.tutoringRepository = tutoringRepository;
        this.userRepository = userRepository;
    }


    @GetMapping(value={"/home", "/"})   //the statistics method should handle GET requests from both /home and /
    public String statistics(Model model) {

        List<TutoringOffer> countInformaticsTutorials = tutoringRepository.findAllByCategoryId(2L);

        List<TutoringOffer> countMathematicsTutorials = tutoringRepository.findAllByCategoryId(1L);

        List<TutoringOffer> countDatascienceTutorials = tutoringRepository.findAllByCategoryId(3L);

        List<User> countAllUsers= userRepository.findAll();


        model.addAttribute("countInformaticsTutorials", countInformaticsTutorials.size());

        model.addAttribute("countMathematicsTutorials", countMathematicsTutorials.size());

        model.addAttribute("countDatascienceTutorials", countDatascienceTutorials.size());

        model.addAttribute("countAllUsers", countAllUsers.size()-1); // minus 1 because we don't count the admin as a user

        return "home";
    }

    @GetMapping("/about-us")
    public String aboutUs(){
        return "about-us";
    }

}
