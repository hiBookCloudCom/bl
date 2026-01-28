package com.example.recommendation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reading")
public class ReadingEntity {

    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_id")
    private Long bookId;

    private String status;

    public Long getUserId() {
        return userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getStatus() {
        return status;
    }
}
