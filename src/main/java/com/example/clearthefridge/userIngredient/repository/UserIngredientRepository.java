package com.example.clearthefridge.userIngredient.repository;

import com.example.clearthefridge.user.entity.User;
import com.example.clearthefridge.userIngredient.entity.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {

    List<UserIngredient> findByUser(User user);

    List<UserIngredient> findByUserId(Long userId);
}
