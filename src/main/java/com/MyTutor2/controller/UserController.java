package com.MyTutor2.controller;

import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.DTOs.UserLogInDTO;
import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.ExRateService;
import com.MyTutor2.service.TutoringService;
import com.MyTutor2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;
    private TutoringService tutoringService;
    private ExRateService exRateService;

    public UserController(UserService userService, UserRepository userRepository, TutoringService tutoringService, ExRateService exRateService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tutoringService = tutoringService;
        this.exRateService = exRateService;
    }

    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO initUserRegisterDTO(){
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }


    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){

            redirectAttributes.addFlashAttribute("userRegisterDTO",userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO",bindingResult);

            return "redirect:register";
        }

        userService.registerUser(userRegisterDTO);
        return "redirect:login";
    }

    @ModelAttribute("userLogInDTO")
    public UserLogInDTO initUserLogInDTO(){
        return new UserLogInDTO();
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/about-us")
    public String aboutUs(){
        return "about-us";
    }

    @RequestMapping(value = "/my-information", method = RequestMethod.GET)
    public String myInformation(@AuthenticationPrincipal UserDetails userDetails, Model model){

        User logedInUser= userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        List<TutorialViewDTO> submittedByMeTutorialsAsView = tutoringService.findAllTutoringOffersByUserId(logedInUser.getId());

        double averagePriceBGN = submittedByMeTutorialsAsView.stream()
                .mapToDouble(TutorialViewDTO::getPrice)
                .average()
                .orElse(0.0);


        BigDecimal averagePriceEUR = exRateService.convert("BGN","EUR",BigDecimal.valueOf(averagePriceBGN));

        model.addAttribute("userName",logedInUser.getName());
        model.addAttribute("userEmail",logedInUser.getEmail());
        model.addAttribute("numberOfClasses",submittedByMeTutorialsAsView.size());
        model.addAttribute("submittedByMeTutorialsAsView",submittedByMeTutorialsAsView);

        DecimalFormat df = new DecimalFormat("#.00");

        model.addAttribute("averagePriceBGN", df.format(averagePriceBGN));
        model.addAttribute("averagePriceEUR", df.format(averagePriceEUR));



        return "my-information";

    }


}
