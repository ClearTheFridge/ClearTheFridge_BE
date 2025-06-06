package com.example.clearthefridge.repository;

import com.example.clearthefridge.entity.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {

}
