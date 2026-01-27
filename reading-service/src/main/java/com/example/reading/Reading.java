package com.example.reading;


import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;
import java.time.Instant;



import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Table(name="reading")
public class Reading {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="user_id", nullable = false)
    private Integer userId;

    @Column(name="book_id")
    private Integer bookId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private ReadingStatus status;

    @Column(name="rating")
    private Integer rating;

    @Column(name="notes", length = 4000)
    private String notes;

    @Column(name="progress_pages")
    private Integer progressPages;

    @Column(name="progress_percent")
    private Integer progressPercent;

    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reading book = (Reading) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
