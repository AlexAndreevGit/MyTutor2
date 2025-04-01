package com.MyTutor2.controller;


import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.service.TutorialsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/tutorials")
public class TutorialsController {

    private TutorialsService tutorialsService;

    public TutorialsController(TutorialsService tutorialsService) {
        this.tutorialsService = tutorialsService;
    }

    @GetMapping("/add")
    public String login() {
        return "tutorial-add";
    }

    //Populating the model with data to be made available to the view before rendering
    @ModelAttribute("tutorialAddDTO")
    public TutorialAddDTO initTutorialAddDTO() {
        return new TutorialAddDTO();
    }


//    @Valid TutorialAddDTO tutorialAddDTO - we make a validation. Validate that the submitted input information fulfill the DTO(@Size(), @NotNull, @Positiv ) validation criteria
    @PostMapping("/add")
    public String createTutorial(@AuthenticationPrincipal UserDetails userDetails,  // source: Spring security
                                 @Valid TutorialAddDTO tutorialAddDTO,              // source: HTTP request
                                 BindingResult bindingResult,                       // source: Spring MVC
                                 RedirectAttributes redirectAttributes) {           // source: Spring MVC


        //    BindingResult bindingResult - through bindingResult we can access the result(errors) from the validation
        if (bindingResult.hasErrors()) {

            //redirectAttributes will save the information in the DTO and errors for short time
            redirectAttributes.addFlashAttribute("tutorialAddDTO", tutorialAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.tutorialAddDTO", bindingResult);

            return "redirect:add";

        }

        String userName = userDetails.getUsername();

        tutorialsService.addTutoringOffer(tutorialAddDTO,userName);

        return "redirect:/";
    }


    @GetMapping("/info")
    public String informaticsOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> informaticsTutorialsAsView = tutorialsService.findAllByCategoryID(2L);

        model.addAttribute("informaticsTutorialsAsView", informaticsTutorialsAsView);

        return "tutorialsInformatics";

    }

    @GetMapping("/math")
    public String mathematicsOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {  //SpringSecurity_8  Use @AuthenticationPrincipal

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> mathematicsTutorialsAsView = tutorialsService.findAllByCategoryID(1L);
        model.addAttribute("mathematicsTutorialsAsView", mathematicsTutorialsAsView);

        return "tutorialsMathematics";

    }

    @GetMapping("/data")
    public String datascienceOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> datascienceTutorialsAsView = tutorialsService.findAllByCategoryID(3L);
        model.addAttribute("datascienceTutorialsAsView", datascienceTutorialsAsView);

        return "tutorialsDatascience";

    }


    @GetMapping("/remove/{id}")    // <a class="ml-3 text-danger" th:href="@{/tutoriels/remove/{id}(id = *{id})}">Remove</a>
    public String remove(@PathVariable Long id){

        tutorialsService.removeOfferById(id);

        return "redirect:/home";
    }


}
