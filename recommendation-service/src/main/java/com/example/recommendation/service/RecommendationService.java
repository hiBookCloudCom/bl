package com.example.recommendation.service;

import com.example.recommendation.dto.RecommendationDto;
import com.example.recommendation.entity.BookEntity;
import com.example.recommendation.entity.UserEntity;
import com.example.recommendation.repo.BookRepository;
import com.example.recommendation.repo.ReadingRepository;
import com.example.recommendation.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final UserRepository userRepository;
    private final ReadingRepository readingRepository;
    private final BookRepository bookRepository;

    public RecommendationService(UserRepository userRepository,
                                 ReadingRepository readingRepository,
                                 BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.readingRepository = readingRepository;
        this.bookRepository = bookRepository;
    }

    public List<RecommendationDto> recommend(Long userId, int limit) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String genre = user.getFavGanre() != null ? user.getFavGanre() : "fiction";

        List<Long> readBooks = readingRepository.findReadBookIds(userId);

        List<BookEntity> candidates = readBooks.isEmpty()
                ? bookRepository.findByGenreIgnoreCase(genre)
                : bookRepository.findBooksNotRead(readBooks);

        return candidates.stream()
                .filter(b -> genre.equalsIgnoreCase(b.getGenre()))
                .limit(limit)
                .map(b -> new RecommendationDto(
                        b.getBookId(),
                        b.getBookName(),
                        b.getAuthor(),
                        b.getGenre(),
                        "Based on your favorite genre: " + genre
                ))
                .toList();
    }
}
