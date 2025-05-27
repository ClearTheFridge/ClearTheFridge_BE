package com.example.clearthefridge.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "recipe_reviews")
public class RecipeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;

    private String content;
    private Integer star;
}