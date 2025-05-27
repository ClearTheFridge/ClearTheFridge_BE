package com.example.clearthefridge.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipes")
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