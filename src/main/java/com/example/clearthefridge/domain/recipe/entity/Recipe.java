package com.example.clearthefridge.domain.recipe.entity;


import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.domain.user.entity.UserLike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String recipeName;
    private String imageUrl;
    private String recipeContext;
    private Double starAvg;
    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeReview> reviews;

    @OneToMany(mappedBy = "recipe")
    private List<UserLike> likes;

    @CreatedDate
    private LocalDateTime createdAt;
}