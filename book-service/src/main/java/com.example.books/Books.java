package com.example.books;


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


    @Column(name="genre")
    private String genre;

    @Column(name="google_volume_id")
    private String googleVolumeId;



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
