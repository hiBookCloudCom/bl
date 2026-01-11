package com.example.users;

public interface UserService {
    UserDTO create(UserDTO dto);
    UserDTO update(UserDTO dto);
    void delete(Integer userId);

    UserDTO get(Integer userId);
    UserDTO getByExternalId(String externalId);
    UserDTO getByEmail(String email);
}
