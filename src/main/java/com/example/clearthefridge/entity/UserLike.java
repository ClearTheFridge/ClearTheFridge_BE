package com.example.clearthefridge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_likes")
public class UserLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Recipe recipe;
}