package com.example.cloudcomp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksDAO extends JpaRepository<Books, Integer> {

    List<Books> findAllByAuthor(String author);
    List<Books> findAllByGenre(String genre);
    List<Books> findAllByBookName(String bookName);
    boolean existsByBookNameAndAuthor(String bookName, String author);
    @Query("select b from Books b where b.bookName like %:q%")
    List<Books> searchSubs(@Param("q") String q);

}
