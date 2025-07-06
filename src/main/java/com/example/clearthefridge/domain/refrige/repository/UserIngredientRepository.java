package com.example.clearthefridge.domain.refrige.repository;

import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.domain.refrige.entity.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, Long> {

    List<UserIngredient> findByUser(User user);

    List<UserIngredient> findByUserId(Long userId);

    List<UserIngredient>  findByUserIdAndIngredientIdIn(Long userId,List<Long> ids);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserIngredient u WHERE u.expiryDate < :today")
    void deleteByExpiryDateAfter(LocalDateTime today);
}
