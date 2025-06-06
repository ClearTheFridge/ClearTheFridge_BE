package com.example.clearthefridge.recipeReview.entity;


import com.example.clearthefridge.recipe.entity.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_reviews")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;

    private String content;
    private Integer star;
}