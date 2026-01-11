package com.example.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer userId;
    private String externalId;
    private String email;
    private String username;
    private String favGanre;
    private Integer booksRead;
}
