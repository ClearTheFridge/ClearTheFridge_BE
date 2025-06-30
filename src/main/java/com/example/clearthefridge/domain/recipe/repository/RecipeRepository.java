package com.example.clearthefridge.domain.recipe.repository;

import com.example.clearthefridge.domain.recipe.entity.Recipe;

//단순 조회,수정, 등록, 삭제용
public interface RecipeRepository {
    Recipe save(Recipe recipe);
    Recipe update(Recipe recipe);
    void delete(Recipe recipe);

}

