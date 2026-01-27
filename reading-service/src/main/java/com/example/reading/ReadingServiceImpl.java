package com.example.reading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReadingServiceImpl implements ReadingService {

    private final ReadingDAO readingDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Reading> getMyReadingList(Integer userId) {
        if (userId == null) {
            throw new RuntimeException("userId is required");
        }
        return readingDAO.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    @Override
    public Reading addOrUpdateEntry(Integer userId, Integer bookId, ReadingStatus status) {
        try {
            validateUserAndBook(userId, bookId);
            if (status == null) throw new RuntimeException("status is required");

            Reading entry = readingDAO.findByUserIdAndBookId(userId, bookId)
                    .orElseGet(() -> Reading.builder()
                            .userId(userId)
                            .bookId(bookId)
                            .build());

            entry.setStatus(status);
            entry.setUpdatedAt(Instant.now());

            return readingDAO.save(entry);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error addOrUpdateEntry userId={}, bookId={}, status={}", userId, bookId, status, e);
            throw new RuntimeException("Failed to add/update reading entry", e);
        }
    }

    @Override
    public Reading updateStatus(Integer userId, Integer bookId, ReadingStatus newStatus) {
        try {
            validateUserAndBook(userId, bookId);
            if (newStatus == null) throw new RuntimeException("newStatus is required");

            Reading entry = getRequired(userId, bookId);
            entry.setStatus(newStatus);
            entry.setUpdatedAt(Instant.now());

            return readingDAO.save(entry);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updateStatus userId={}, bookId={}, newStatus={}", userId, bookId, newStatus, e);
            throw new RuntimeException("Failed to update status", e);
        }
    }

    @Override
    public Reading updateProgress(Integer userId, Integer bookId, Integer pages, Integer percent) {
        try {
            validateUserAndBook(userId, bookId);

            if (pages == null && percent == null) {
                throw new RuntimeException("pages or percent is required");
            }
            if (pages != null && pages < 0) throw new RuntimeException("progressPages must be >= 0");
            if (percent != null && (percent < 0 || percent > 100)) {
                throw new RuntimeException("progressPercent must be 0-100");
            }

            Reading entry = getRequired(userId, bookId);
            if (pages != null) entry.setProgressPages(pages);
            if (percent != null) entry.setProgressPercent(percent);
            entry.setUpdatedAt(Instant.now());

            return readingDAO.save(entry);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updateProgress userId={}, bookId={}, pages={}, percent={}",
                    userId, bookId, pages, percent, e);
            throw new RuntimeException("Failed to update progress", e);
        }
    }

    @Override
    public Reading updateRating(Integer userId, Integer bookId, Integer rating) {
        try {
            validateUserAndBook(userId, bookId);

            if (rating == null) throw new RuntimeException("rating is required");
            if (rating < 1 || rating > 5) throw new RuntimeException("rating must be 1-5");

            Reading entry = getRequired(userId, bookId);
            entry.setRating(rating);
            entry.setUpdatedAt(Instant.now());

            return readingDAO.save(entry);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updateRating userId={}, bookId={}, rating={}", userId, bookId, rating, e);
            throw new RuntimeException("Failed to update rating", e);
        }
    }

    @Override
    public Reading updateNotes(Integer userId, Integer bookId, String notes) {
        try {
            validateUserAndBook(userId, bookId);

            if (notes == null) throw new RuntimeException("notes is required");
            if (notes.length() > 4000) throw new RuntimeException("notes max length is 4000");

            Reading entry = getRequired(userId, bookId);
            entry.setNotes(notes);
            entry.setUpdatedAt(Instant.now());

            return readingDAO.save(entry);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updateNotes userId={}, bookId={}", userId, bookId, e);
            throw new RuntimeException("Failed to update notes", e);
        }
    }

    @Override
    public void removeEntry(Integer userId, Integer bookId) {
        try {
            validateUserAndBook(userId, bookId);

            if (!readingDAO.existsByUserIdAndBookId(userId, bookId)) {
                throw new RuntimeException("Reading entry not found for userId=" + userId + ", bookId=" + bookId);
            }
            readingDAO.deleteByUserIdAndBookId(userId, bookId);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error removeEntry userId={}, bookId={}", userId, bookId, e);
            throw new RuntimeException("Failed to remove entry", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reading> getMyReadingListByStatus(Integer userId, ReadingStatus status) {
        if (userId == null) throw new RuntimeException("userId is required");
        if (status == null) throw new RuntimeException("status is required");
        return readingDAO.findByUserIdAndStatusOrderByUpdatedAtDesc(userId, status);
    }


    @Override
    @Transactional(readOnly = true)
    public long countByStatus(Integer userId, ReadingStatus status) {
        if (userId == null) throw new RuntimeException("userId is required");
        if (status == null) throw new RuntimeException("status is required");
        return readingDAO.countByUserIdAndStatus(userId, status);
    }

    private Reading getRequired(Integer userId, Integer bookId) {
        return readingDAO.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Reading entry not found for userId=" + userId + ", bookId=" + bookId));
    }

    private void validateUserAndBook(Integer userId, Integer bookId) {
        if (userId == null ) throw new RuntimeException("userId is required");
        if (bookId == null) throw new RuntimeException("bookId is required");
    }




    @Override
    @Transactional(readOnly = true)
    public Reading getEntry(Integer userId, Integer bookId) {
        validateUserAndBook(userId, bookId);
        return readingDAO.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException(
                        "Reading entry not found for userId=" + userId + ", bookId=" + bookId
                ));
    }


}
