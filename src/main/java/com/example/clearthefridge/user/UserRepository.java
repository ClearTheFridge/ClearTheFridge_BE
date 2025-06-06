package com.example.clearthefridge.user;

import com.example.clearthefridge.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
