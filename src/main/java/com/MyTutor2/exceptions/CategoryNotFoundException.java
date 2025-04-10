package com.MyTutor2.exceptions;

import com.MyTutor2.model.enums.CategoryNameEnum;

public class CategoryNotFoundException extends Exception{

    public CategoryNotFoundException(CategoryNameEnum categoryName){
        super("Category not found with name: " + categoryName);

    }

}
