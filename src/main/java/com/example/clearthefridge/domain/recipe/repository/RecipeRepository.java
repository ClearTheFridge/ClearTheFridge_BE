package com.example.clearthefridge.domain.recipe.repository;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

//단순 조회,수정, 등록, 삭제용
public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    //save(), deleteById(),delete()
}

