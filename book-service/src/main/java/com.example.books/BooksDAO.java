package com.example.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksDAO extends JpaRepository<Books, Integer> {

    List<Books> findAllByAuthor(String author);
    List<Books> findAllByGenre(String genre);


    boolean existsByBookNameAndAuthor(String bookName, String author);
    @Query("select b from Books b where lower(b.bookName) like lower(concat('%', :q, '%'))")
    List<Books> searchSubs(@Param("q") String q);

    boolean existsByGoogleVolumeId(String googleVolumeId);


}
