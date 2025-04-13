package com.MyTutor2.controller;


import com.MyTutor2.exceptions.CategoryNotFoundException;
import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.exceptions.UserNotFoundException;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.service.OpenAIService;
import com.MyTutor2.service.TutorialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/tutorials")   ///tutorials/ask-question
public class TutorialsController {

    private TutorialsService tutorialsService;
    private OpenAIService openAIService;

    public TutorialsController(TutorialsService tutorialsService, OpenAIService openAIService) {
        this.tutorialsService = tutorialsService;
        this.openAIService = openAIService;
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


    //@Valid TutorialAddDTO tutorialAddDTO - we make a validation. Validate that the submitted input information fulfill the DTO(@Size(), @NotNull, @Positiv ) validation criteria
    @PostMapping("/add")
    public String createTutorial(@AuthenticationPrincipal UserDetails userDetails,  // source: Spring security
                                 @Valid TutorialAddDTO tutorialAddDTO,              // source: HTTP request
                                 BindingResult bindingResult,                       // source: Spring MVC
                                 RedirectAttributes redirectAttributes) throws UserNotFoundException, CategoryNotFoundException {           // source: Spring MVC


        //BindingResult bindingResult - through bindingResult we can access the result(errors) from the validation
        if (bindingResult.hasErrors()) {

            //redirectAttributes will save the information in the DTO and errors for short time
            redirectAttributes.addFlashAttribute("tutorialAddDTO", tutorialAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.tutorialAddDTO", bindingResult);

            return "redirect:add";

        }

        String userName = userDetails.getUsername();

        tutorialsService.addTutoringOffer(tutorialAddDTO, userName);

        return "redirect:/";
    }


    // ChatBotAPI_1 -> In commons
    // ChatBotAPI_2
    @PostMapping("/ask-question")
    public ResponseEntity<Map<String, Object>> askQuestion(@RequestBody Map<String, String> payload) { //
        Map<String, Object> response = new HashMap<>();
        String query = payload.get("query");

        String answer = openAIService.askQuestion(query);
        response.put("answer", answer);

        return ResponseEntity.ok(response);
    }

    // ChatBotAPI_3
    @GetMapping("/ask-question")
    public String askQuestion() {
        return "ask-question";
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

    @GetMapping("/other")
    public String otherOffers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> otherTutorialsAsView = tutorialsService.findAllByCategoryID(3L);
        model.addAttribute("otherTutorialsAsView", otherTutorialsAsView);

        return "tutorialsOther";

    }


    @GetMapping("/remove/{id}")
    // <a class="ml-3 text-danger" th:href="@{/tutoriels/remove/{id}(id = *{id})}">Remove</a>
    public String remove(@PathVariable Long id) throws TutorialNotFoundException {

        tutorialsService.removeOfferById(id);

        return "redirect:/home";
    }


}
