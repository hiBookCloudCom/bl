package com.example.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Table(name="users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="user_id")
    private Integer userId;

    @Column(name="external_id", unique = true)
    private String externalId;

    @Column(name="email")
    private String email;

    @Column(name="username")
    private String username;

    @Column(name="fav_ganre")
    private String favGanre;

    @Column(name="books_read")
    private Integer booksRead;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User u)) return false;
        return Objects.equals(userId, u.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

}
