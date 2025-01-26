package com.MyTutor2.service.impl;

import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.entity.UserRoleEntity;
import com.MyTutor2.model.enums.CategoryNameEnum;
import com.MyTutor2.model.enums.UserRoleEnum;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.repo.UserRoleRepository;
import com.MyTutor2.service.InitDataService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitDataServiceImpl implements InitDataService {

    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private TutoringRepository tutoringRepository;
    private UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    // private final PasswordEncoder passwordEncoder;  TODO security add paswordencoder when starting to work with spring security


    public InitDataServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, TutoringRepository tutoringRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tutoringRepository = tutoringRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initData() {


        if (userRepository.count() == 0) {          //TODO make better

            // ----- User roles in the Database -----
            UserRoleEntity userRoleEntityUser = new UserRoleEntity(UserRoleEnum.USER);
            UserRoleEntity userRoleEntityAdmin = new UserRoleEntity(UserRoleEnum.ADMIN);

            userRoleRepository.save(userRoleEntityUser);
            userRoleRepository.save(userRoleEntityAdmin);

            // ----- Users in the Database -----
            User admin = new User("admin1", passwordEncoder.encode("12345"), "admin1@gmail.com");
            List<UserRoleEntity> listAdmin = new ArrayList<>();
            listAdmin.add(userRoleEntityUser);
            listAdmin.add(userRoleEntityAdmin);
            admin.setRoles(listAdmin);
            userRepository.save(admin);

            User user1 = new User("user1", passwordEncoder.encode("12345"), "u1@gmail.com");
            List<UserRoleEntity> list = new ArrayList<>();
            list.add(userRoleEntityUser);
            user1.setRoles(list);
            userRepository.save(user1);

            User user2 = new User("user2", passwordEncoder.encode("12345"), "u2@gmail.com");
            List<UserRoleEntity> listUser2 = new ArrayList<>();
            listUser2.add(userRoleEntityUser);
            user2.setRoles(listUser2);
            userRepository.save(user2);

            User user3 = new User("user3", passwordEncoder.encode("12345"), "u3@gmail.com");
            List<UserRoleEntity> listUser3 = new ArrayList<>();
            listUser3.add(userRoleEntityUser);
            user3.setRoles(listUser3);
            userRepository.save(user3);


            // get the categories from the database
            Category categoryMath = categoryRepository.findByName(CategoryNameEnum.MATHEMATICS);
            Category categoryInfo = categoryRepository.findByName(CategoryNameEnum.INFORMATICS);
            Category categoryData = categoryRepository.findByName(CategoryNameEnum.DATASCIENCE);

            //Create mathematics offers
            TutoringOffer tutoringOfferMath1 = new TutoringOffer("Linear algebra 1", "Description math offer 1", 22.3, categoryMath, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferMath1);

            TutoringOffer tutoringOfferMath2 = new TutoringOffer("Integrals", "Description math offer 2", 22.3, categoryMath, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferMath2);

            TutoringOffer tutoringOfferMath3 = new TutoringOffer("Linear algebra 1", "Description math offer 1", 32.3, categoryMath, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferMath3);

            //Create informatics offers
            TutoringOffer tutoringOfferInfo1 = new TutoringOffer("Java advanced", "Description info offer 1", 32.3, categoryInfo, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferInfo1);

            TutoringOffer tutoringOfferInfo2 = new TutoringOffer("SQL", "Description info offer 2", 15.3, categoryInfo, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferInfo2);

            TutoringOffer tutoringOfferInfo3 = new TutoringOffer("Python", "Description info offer 1", 37.3, categoryInfo, userRepository.findByUsername("user3").get());
            tutoringRepository.save(tutoringOfferInfo3);

            //Create datascience offers
            TutoringOffer tutoringOfferData1 = new TutoringOffer("Data science for beginners", "Description datascience offer 1", 32.3, categoryData, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferData1);


        }


    }


}
