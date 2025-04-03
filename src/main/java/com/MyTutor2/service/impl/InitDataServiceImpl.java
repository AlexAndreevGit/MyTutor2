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


    public InitDataServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, TutoringRepository tutoringRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tutoringRepository = tutoringRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initData() {


        if (userRepository.count() == 0) {

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
            Category categoryData = categoryRepository.findByName(CategoryNameEnum.OTHER);

            //Create mathematics offers
            TutoringOffer tutoringOfferMath1 = new TutoringOffer("Advanced Calculus for STEM Majors", "Deepen your understanding of multivariable calculus, limits, integrals, and series through clear explanations and examples.", 20.0, categoryMath, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferMath1);

            TutoringOffer tutoringOfferMath2 = new TutoringOffer("Linear Algebra for Engineers", "Explore vector spaces, matrix operations, and eigenvalues with practical applications in engineering and computer science.", 15.0, categoryMath, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferMath2);

            TutoringOffer tutoringOfferMath3 = new TutoringOffer("Real Analysis: Foundations of Calculus", "Understand sequences, continuity, and rigorous proofs to build a solid foundation in real mathematical analysis.", 25.0, categoryMath, userRepository.findByUsername("user3").get());
            tutoringRepository.save(tutoringOfferMath3);

            TutoringOffer tutoringOfferMath4 = new TutoringOffer("Numerical Methods for Applied Mathematics", "Study root-finding, numerical integration, and differential equation solving using computational techniques and algorithms.", 18.0, categoryMath, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferMath4);

            TutoringOffer tutoringOfferMath5 = new TutoringOffer("Discrete Mathematics for Computer Science", "Dive into logic, set theory, combinatorics, and graph theory—essential tools for theoretical computer science and algorithms.", 15.0, categoryMath, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferMath5);


            //Create informatics offers
            TutoringOffer tutoringOfferInfo1 = new TutoringOffer("Introduction to Programming with Python", "Learn programming fundamentals, data types, loops, and functions using Python—perfect for beginners in Informatics.", 30.5, categoryInfo, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferInfo1);

            TutoringOffer tutoringOfferInfo2 = new TutoringOffer("Object-Oriented Programming in Java", "Understand classes, inheritance, and polymorphism to build structured and maintainable Java applications from scratch.", 15.5, categoryInfo, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferInfo2);

            TutoringOffer tutoringOfferInfo3 = new TutoringOffer("Web Development with HTML, CSS, and JavaScript", "Build responsive and interactive websites using core web technologies and modern front-end development practices.", 30.5, categoryInfo, userRepository.findByUsername("user3").get());
            tutoringRepository.save(tutoringOfferInfo3);

            TutoringOffer tutoringOfferInfo4 = new TutoringOffer("Databases and SQL Fundamentals", "Learn relational database design, normalization, and how to query data efficiently using SQL commands and joins.", 20.0, categoryInfo, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferInfo4);

            TutoringOffer tutoringOfferInfo5 = new TutoringOffer("Software Engineering Principles", "Discover the software development lifecycle, version control, and testing to develop reliable and scalable software systems.", 17.5, categoryInfo, userRepository.findByUsername("user2").get());
            tutoringRepository.save(tutoringOfferInfo5);

            TutoringOffer tutoringOfferInfo6 = new TutoringOffer("Operating Systems and Computer Architecture", "Gain insights into processes, memory management, file systems, and how hardware and software interact.", 18.5, categoryInfo, userRepository.findByUsername("user3").get());
            tutoringRepository.save(tutoringOfferInfo6);

            //Create other offers
            TutoringOffer tutoringOfferOther1 = new TutoringOffer("Data Visualization and Storytelling", "Create insightful visualizations using tools like Seaborn, Plotly, and Tableau to communicate data-driven findings effectively.", 20.0, categoryData, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferOther1);

            TutoringOffer tutoringOfferOther2 = new TutoringOffer("Introduction to Data Science with Python", "Learn data cleaning, visualization, and analysis using pandas, NumPy, and matplotlib in practical Python projects.", 25.5, categoryData, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferOther2);

            TutoringOffer tutoringOfferOther3 = new TutoringOffer("Big Data and Cloud Analytics", "Explore Hadoop, Spark, and cloud platforms to process and analyze large-scale data efficiently.", 18.0, categoryData, userRepository.findByUsername("user1").get());
            tutoringRepository.save(tutoringOfferOther3);

        }


    }


}
