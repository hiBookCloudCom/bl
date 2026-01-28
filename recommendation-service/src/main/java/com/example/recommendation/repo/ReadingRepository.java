package com.example.recommendation.repo;

import com.example.recommendation.entity.ReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadingRepository extends JpaRepository<ReadingEntity, Long> {

    @Query("select r.bookId from ReadingEntity r where r.userId = :userId")
    List<Long> findReadBookIds(@Param("userId") Long userId);
}
