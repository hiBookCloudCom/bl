package com.example.reading;

import java.util.List;

public interface ReadingService {


    List<Reading> getMyReadingList(Integer userId);
    Reading addOrUpdateEntry(Integer userId, Integer bookId, ReadingStatus status);
    Reading updateStatus(Integer userId, Integer bookId, ReadingStatus newStatus);
    Reading updateProgress(Integer userId, Integer bookId, Integer pages, Integer percent);
    Reading updateRating(Integer userId, Integer bookId, Integer rating);
    Reading updateNotes(Integer userId, Integer bookId, String notes);
    void removeEntry(Integer userId, Integer bookId);
    long countByStatus(Integer userId, ReadingStatus status);
    List<Reading> getMyReadingListByStatus(Integer userId, ReadingStatus status);

    Reading getEntry(Integer userId, Integer bookId);




}
