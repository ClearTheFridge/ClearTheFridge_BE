package com.example.clearthefridge.global.scheduler;

import com.example.clearthefridge.domain.refrige.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ExpiredIngredientScheduler {

    private final UserIngredientRepository userIngredientRepository;

    @Scheduled(cron = "0 0 0,1 * * *")
    public void deleteExpiredIngredients() {
        LocalDateTime today = LocalDateTime.now();
        userIngredientRepository.deleteByExpiryDateAfter(today);
    }
}
