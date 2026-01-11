package com.example.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {
    Optional<User> findByExternalId(String externalId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByExternalId(String externalId);


}
