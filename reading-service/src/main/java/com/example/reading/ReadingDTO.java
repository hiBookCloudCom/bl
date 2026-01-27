package com.example.reading;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadingDTO {

    private Integer id;
    private Integer userId;
    private Integer bookId;
    private ReadingStatus status;
    private Integer rating;
    private String notes;
    private Integer progressPages;
    private Integer progressPercent;
    private Instant updatedAt;
}
