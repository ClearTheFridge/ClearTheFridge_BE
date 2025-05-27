package com.example.clearthefridge.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_ingredients")
public class UserIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Ingredient ingredient;

    private String amount;
    private LocalDateTime expiryDate;
}