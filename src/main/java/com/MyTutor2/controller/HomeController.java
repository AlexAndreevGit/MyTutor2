package com.MyTutor2.controller;

import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.TutoringService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private TutoringService tutoringService;
    private TutoringRepository tutoringRepository;
    private UserRepository userRepository;

    public HomeController(TutoringService tutoringService, TutoringRepository tutoringRepository, UserRepository userRepository) {
        this.tutoringService = tutoringService;
        this.tutoringRepository = tutoringRepository;
        this.userRepository = userRepository;
    }



    @GetMapping("/info")
    public String informaticsOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> informaticsTutorialsAsView = tutoringService.findAllByCategoryID(2L);

        model.addAttribute("informaticsTutorialsAsView", informaticsTutorialsAsView);

        return "homeInformatics";

    }

    @GetMapping("/math")
    public String mathematicsOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {  //SpringSecurity_8  Use @AuthenticationPrincipal

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> mathematicsTutorialsAsView = tutoringService.findAllByCategoryID(1L);
        model.addAttribute("mathematicsTutorialsAsView", mathematicsTutorialsAsView);

        return "homeMathematics";

    }

    @GetMapping("/data")
    public String datascienceOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> datascienceTutorialsAsView = tutoringService.findAllByCategoryID(3L);
        model.addAttribute("datascienceTutorialsAsView", datascienceTutorialsAsView);

        return "homeDatascience";

    }


    @GetMapping(value={"/home", "/"})   //aan "/home", "/"}
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
