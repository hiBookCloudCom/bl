package com.example.recommendation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @Column(name = "book_id")
    private Long bookId;

    private String author;

    @Column(name = "book_name")
    private String bookName;

    private String genre;
    private Integer rating;

    @Column(name = "google_volume_id")
    private String googleVolumeId;

    public Long getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getRating() {
        return rating;
    }
}
