package com.example.clearthefridge.domain.refrige.entity;
import com.example.clearthefridge.domain.ingredient.entity.Ingredient;
import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.global.exception.CustomException;
import com.example.clearthefridge.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Table(name = "user_ingredients")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Ingredient ingredient;

    //재료 단위 ex g, ml,
    private String unit;

    //재료 수량
    private int amount;

    //유통기한 만료일
    private LocalDateTime expiryDate;

    //재료 등록일
    @CreatedDate
    private LocalDateTime createdAt;

    public void decreaseAmount(int consumeAmount) {
        if (this.amount < consumeAmount) {
            throw new CustomException(ErrorCode.NOT_ENOUGH_INGREDIENTS);
        }
        this.amount -= consumeAmount;
    }


}