package com.example.reading;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import java.util.List;

@Repository
public interface ReadingDAO extends JpaRepository<Reading, Integer> {


    List<Reading> findByUserIdOrderByUpdatedAtDesc(Integer userId);

    Optional<Reading> findByUserIdAndBookId(Integer userId, Integer bookId);

    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);

    List<Reading> findByUserIdAndStatusOrderByUpdatedAtDesc(Integer userId, ReadingStatus status);

    void deleteByUserIdAndBookId(Integer userId, Integer bookId);

    long countByUserIdAndStatus(Integer userId, ReadingStatus status);



}
