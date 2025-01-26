package com.MyTutor2.repo;

import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(CategoryNameEnum categoryNameEnum);

}
