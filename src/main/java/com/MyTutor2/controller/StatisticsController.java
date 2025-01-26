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
//TODO security @RequwstMapping Admin
public class StatisticsController {

    private TutoringRepository tutoringRepository;
    private UserRepository userRepository;


    public StatisticsController(TutoringRepository tutoringRepository, UserRepository userRepository) {
        this.tutoringRepository = tutoringRepository;
        this.userRepository = userRepository;
    }

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


//                    <h3 class="mySticky bg-gray text-dark  mb-0 mt-2" th:text="|Number of the Informatics offers: ${countInformticsTutorials}|"></h3>
//                </div>
//
//                <div>
//                    <h3 class="mySticky bg-gray text-dark  mb-0 mt-2" th:text="|Number of the Mathematics offers: ${countMathematicsTutorials}|"></h3>
//                </div>
//
//                <div>
//                    <h3 class="mySticky bg-gray text-dark  mb-0 mt-2" th:text="|Number of the Datascience offers: ${countDatascienceTutorials}|"></h3>
//                </div>
//
//                <div>
//                    <h3 class="mySticky bg-gray text-dark  mb-0 mt-2" th:text="|Number of all registered tutors: ${countAllUsers}|"></h3>