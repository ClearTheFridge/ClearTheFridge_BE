package com.example.clearthefridge.domain.user.repository;

import com.example.clearthefridge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
