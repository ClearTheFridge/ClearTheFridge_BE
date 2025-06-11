package com.example.clearthefridge.domain.recipe.entity;


import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.domain.user.entity.UserLike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String recipeName;
    private String imageUrl;
    private Double starAvg;
    private Integer likeCount;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeReview> reviews;

    @OneToMany(mappedBy = "recipe")
    private List<UserLike> likes;
}