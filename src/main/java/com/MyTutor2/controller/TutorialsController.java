package com.MyTutor2.controller;


import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.service.TutoringService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/tutoriels")
public class TutorialsController {

    private TutoringService tutoringService;

    public TutorialsController(TutoringService tutoringService) {
        this.tutoringService = tutoringService;
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
//    RedirectAttributes redirectAttributes -

    @PostMapping("/add")
    public String createTutorial(@AuthenticationPrincipal UserDetails userDetails,
                                 @Valid TutorialAddDTO tutorialAddDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {


        //    BindingResult bindingResult - through bindingResult we can access the result(errors) from the validation
        if (bindingResult.hasErrors()) {

            //redirectAttributes will save the information in the DTO and errors for short time
            redirectAttributes.addFlashAttribute("tutorialAddDTO", tutorialAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.tutorialAddDTO", bindingResult);

            return "redirect:add";

        }

        String userName = userDetails.getUsername();

        tutoringService.addTutoringOffer(tutorialAddDTO,userName);


        return "redirect:/";
    }


    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id){

        tutoringService.removeOfferById(id);

        return "redirect:/";
    }


}
