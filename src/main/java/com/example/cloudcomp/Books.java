package com.example.cloudcomp;


import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Table(name="books")
public class Books {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="book_id")
    private Integer bookId;

    @Column(name="book_name")
    private String bookName;

    @Column(name="author")
    private String author;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "genre", referencedColumnName = "genre_name")
    @Column(name="genre")
    private String genre;


    @Column(name="user_id", nullable = false)
    private Integer userId;

    @Column(name="google_volume_id")
    private String googleVolumeId;

    @Column(name="rating")
    private Integer rating;

    @Column(name="status")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return Objects.equals(bookId, books.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }
}
