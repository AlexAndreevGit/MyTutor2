package com.myTutor2.service.impl;

import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.enums.CategoryNameEnum;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)  //used to enable mockitos features such as @Mock, @InjectMocks
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    @InjectMocks
    private CategoryServiceImpl mockCategoryServiceImpl;

    @Test
    void testCategoryInitialisation(){

        mockCategoryServiceImpl.initCategories();

        //verify that the method save is called 3 times on mockCategoryRepository.
        verify(mockCategoryRepository,times(3)).save(any(Category.class));

    }

}
