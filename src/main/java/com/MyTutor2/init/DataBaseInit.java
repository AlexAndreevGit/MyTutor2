package com.MyTutor2.init;

import com.MyTutor2.service.CategoryService;
import com.MyTutor2.service.InitDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


//The commandLineRunner is coming from Spring.
//It contains only one method -> run()

@Component
public class DataBaseInit implements CommandLineRunner {

    private CategoryService categoryService;
    private InitDataService initDataService;

    public DataBaseInit(CategoryService categoryService, InitDataService initDataService) {
        this.categoryService = categoryService;
        this.initDataService = initDataService;
    }


    //the run() method is triggered after Spring Boot has started.
    @Override
    public void run(String... args) throws Exception {

        categoryService.initCategories();
        initDataService.initData();

    }
}
