package com.example.clearthefridge.domain.recipe.refrige.repository;

import com.example.clearthefridge.domain.recipe.refrige.entity.UserIngredient;
import com.example.clearthefridge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {

    List<UserIngredient> findByUser(User user);

    List<UserIngredient> findByUserId(Long userId);
}
