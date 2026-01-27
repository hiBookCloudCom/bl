package com.example.reading;


import org.springframework.stereotype.Component;

@Component
public class ReadingMapper {

    public ReadingDTO toDto(Reading r) {
        if (r == null) return null;

        return ReadingDTO.builder()
                .id(r.getId())
                .userId(r.getUserId())
                .bookId(r.getBookId())
                .status(r.getStatus())
                .rating(r.getRating())
                .notes(r.getNotes())
                .progressPages(r.getProgressPages())
                .progressPercent(r.getProgressPercent())
                .updatedAt(r.getUpdatedAt())
                .build();
    }

    public Reading toEntity(ReadingDTO dto) {
        if (dto == null) return null;

        return Reading.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .bookId(dto.getBookId())
                .status(dto.getStatus())
                .rating(dto.getRating())
                .notes(dto.getNotes())
                .progressPages(dto.getProgressPages())
                .progressPercent(dto.getProgressPercent())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}