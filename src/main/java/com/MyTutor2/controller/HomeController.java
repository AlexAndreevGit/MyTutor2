package com.MyTutor2.controller;

import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.repo.TutoringRepository;
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

    public HomeController(TutoringService tutoringService) {
        this.tutoringService = tutoringService;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetails userDetails) {                      //TODO AutetificationPricipal

        if (userDetails == null) {        //is the user is not logged in then we won't have userDetails
            return "index";
        }

        return "home";

    }


    @GetMapping("/info")
    public String informaticsOffers(Model model) {                      //TODO AutetificationPricipal

        //TODO if user not loged in then show index

        List<TutorialViewDTO> informaticsTutorialsAsView = tutoringService.findAllByCategoryID(2L);
        model.addAttribute("informaticsTutorialsAsView", informaticsTutorialsAsView);

        return "homeInformatics";

    }

    @GetMapping("/math")
    public String mathematicsOffers(Model model) {                      //TODO AutetificationPricipal

        //TODO if user not loged in then show index

        List<TutorialViewDTO> mathematicsTutorialsAsView = tutoringService.findAllByCategoryID(1L);
        model.addAttribute("mathematicsTutorialsAsView", mathematicsTutorialsAsView);

        return "homeMathematics";

    }

    @GetMapping("/data")
    public String datascienceOffers(Model model) {                      //TODO AutetificationPricipal

        //TODO if user not loged in then show index

        List<TutorialViewDTO> datascienceTutorialsAsView = tutoringService.findAllByCategoryID(3L);
        model.addAttribute("datascienceTutorialsAsView", datascienceTutorialsAsView);

        return "homeDatascience";

    }



    @GetMapping("/home")
    public String indexPage() {

        return "index";

    }

}
