package com.example.recommendation.repo;

import com.example.recommendation.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByGenreIgnoreCase(String genre);

    @Query("select b from BookEntity b where b.bookId not in :excluded")
    List<BookEntity> findBooksNotRead(@Param("excluded") List<Long> excluded);
}
