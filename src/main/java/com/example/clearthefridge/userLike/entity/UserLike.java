package com.example.clearthefridge.userLike.entity;

import com.example.clearthefridge.recipe.entity.Recipe;
import com.example.clearthefridge.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_likes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Recipe recipe;
}