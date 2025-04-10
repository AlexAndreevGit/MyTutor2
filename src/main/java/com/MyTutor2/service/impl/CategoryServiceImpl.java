package com.MyTutor2.service.impl;

import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.enums.CategoryNameEnum;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {

        if (categoryRepository.count() != 0) {
            return;                                 //a void function can also return so the following code is not executed
        }

        for (CategoryNameEnum currentCategoryEnum : CategoryNameEnum.values()) {

            Category category = new Category(currentCategoryEnum);

            switch (currentCategoryEnum) {
                case MATHEMATICS:
                    category.setDescription("Mathematics is the abstract science of number, quantity, and space, which can be applied to patterns, structures, and changes in the physical world through logical reasoning and quantitative calculations.");
                    break;
                case OTHER:
                    category.setDescription("Data science is the multidisciplinary field that combines statistical analysis, machine learning, and domain expertise to extract insights and knowledge from structured and unstructured data.");
                    break;
                case INFORMATICS:
                    category.setDescription("Informatics is the interdisciplinary study of information processing, management, and dissemination, encompassing the design, implementation, and evaluation of computational systems and their impact on society.");
                    break;
            }

            categoryRepository.save(category);

        }

    }

}
