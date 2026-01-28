package com.example.recommendation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "fav_ganre")
    private String favGanre;

    public Long getUserId() {
        return userId;
    }

    public String getFavGanre() {
        return favGanre;
    }
}
