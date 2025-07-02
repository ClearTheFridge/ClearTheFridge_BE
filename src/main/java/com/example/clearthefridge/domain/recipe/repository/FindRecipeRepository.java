package com.example.clearthefridge.domain.recipe.repository;
//JPA 조회용 repo

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FindRecipeRepository extends JpaRepository {

    //키워드를 포함하고 있는 제목 추출
    List<Recipe> findByrecipeNameContaining(String keyword);

    //사용자 이름으로 레시피 추출
    List<Recipe> findByUser_username(String username);
}
