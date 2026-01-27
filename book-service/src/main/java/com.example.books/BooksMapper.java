package com.example.books;


import org.springframework.stereotype.Component;

@Component
public class BooksMapper {

    public BooksDTO toDto(Books b) {
        if (b == null) return null;

        return BooksDTO.builder()
                .bookId(b.getBookId())
                .bookName(b.getBookName())
                .author(b.getAuthor())
                .genre(b.getGenre())
                .googleVolumeId(b.getGoogleVolumeId())
                .build();
    }

    public Books toEntity(BooksDTO dto) {
        if (dto == null) return null;

        return Books.builder()
                .bookId(dto.getBookId())
                .bookName(dto.getBookName())
                .author(dto.getAuthor())
                .genre(dto.getGenre())
                .googleVolumeId(dto.getGoogleVolumeId())
                .build();
    }
}