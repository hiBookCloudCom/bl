package com.example.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO dao;
    private final UserMapper mapper;

    @Override
    public UserDTO create(UserDTO dto) {
        try {
            if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                throw new RuntimeException("email is required");
            }

            String email = dto.getEmail().trim().toLowerCase();

            if (dto.getExternalId() != null && dao.existsByExternalId(dto.getExternalId())) {
                throw new RuntimeException("externalId already exists");
            }
            if (dao.existsByEmail(email)) {
                throw new RuntimeException("email already exists");
            }

            User u = User.builder()
                    .externalId(dto.getExternalId())
                    .email(email)
                    .username(dto.getUsername())
                    .booksRead(dto.getBooksRead())
                    .favGanre(dto.getFavGanre())
                    .build();

            return mapper.toDto(dao.save(u));

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating user: {}", dto, e);
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    public UserDTO update(UserDTO dto) {
        try {
            if (dto.getUserId() == null) throw new RuntimeException("userId is required");

            User updated = dao.findById(dto.getUserId())
                    .map(u -> {
                        if (dto.getEmail() != null) {
                            String email = dto.getEmail().trim().toLowerCase();
                            if (!email.equals(u.getEmail()) && dao.existsByEmail(email)) {
                                throw new RuntimeException("email already exists");
                            }
                            u.setEmail(email);
                        }

                        if (dto.getExternalId() != null) {
                            if (!dto.getExternalId().equals(u.getExternalId()) && dao.existsByExternalId(dto.getExternalId())) {
                                throw new RuntimeException("externalId already exists");
                            }
                            u.setExternalId(dto.getExternalId());
                        }

                        if (dto.getUsername() != null) u.setUsername(dto.getUsername());
                        if (dto.getBooksRead() != null) u.setBooksRead(dto.getBooksRead());
                        if (dto.getFavGanre() != null) u.setFavGanre(dto.getFavGanre());

                        return dao.save(u);
                    })
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));

            return mapper.toDto(updated);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updating user: {}", dto, e);
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    public void delete(Integer userId) {
        try {
            if (userId == null) throw new RuntimeException("userId is required");
            if (!dao.existsById(userId)) throw new RuntimeException("User not found: " + userId);
            dao.deleteById(userId);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting userId: {}", userId, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    @Transactional
    public UserDTO get(Integer userId) {
        if (userId == null) throw new RuntimeException("userId is required");
        return dao.findById(userId).map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    @Override
    @Transactional
    public UserDTO getByExternalId(String externalId) {
        if (externalId == null || externalId.isBlank()) throw new RuntimeException("externalId is required");
        return dao.findByExternalId(externalId).map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found: externalId=" + externalId));
    }

    @Override
    @Transactional
    public UserDTO getByEmail(String email) {
        if (email == null || email.isBlank()) throw new RuntimeException("email is required");
        String normalized = email.trim().toLowerCase();
        return dao.findByEmail(normalized).map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found: email=" + normalized));
    }
}
