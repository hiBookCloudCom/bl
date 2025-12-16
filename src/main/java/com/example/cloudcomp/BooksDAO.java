package com.example.cloudcomp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksDAO extends JpaRepository<Books, Integer> {

    List<Books> findAllByAuthorAndUserId(String author, Integer userId);
    List<Books> findAllByGenreAndUserId(String genre, Integer userId);
    List<Books> findAllByBookNameAndUserId(String bookName, Integer userId);

    List<Books> findAllByUserId(Integer userId);
    List<Books> findAllByRatingAndUserId(Integer rating, Integer userId);
    boolean existsByBookNameAndAuthor(String bookName, String author);
    @Query("select b from Books b where b.bookName like %:q%")
    List<Books> searchSubs(@Param("q") String q);

    boolean existsByGoogleVolumeIdAndUserId(String googleVolumeId, Integer userId);


}
