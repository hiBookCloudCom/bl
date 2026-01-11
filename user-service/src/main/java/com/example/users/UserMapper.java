package com.example.users;

import org.springframework.stereotype.Component;

@ Component

public class UserMapper {
    public UserDTO toDto(User u) {
        if (u == null) return null;
        return UserDTO.builder()
                .userId(u.getUserId())
                .externalId(u.getExternalId())
                .email(u.getEmail())
                .username(u.getUsername())
                .favGanre(u.getFavGanre())
                .booksRead(u.getBooksRead())
                .build();
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .userId(dto.getUserId())
                .externalId(dto.getExternalId())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .favGanre(dto.getFavGanre())
                .booksRead(dto.getBooksRead())
                .build();
    }
}
