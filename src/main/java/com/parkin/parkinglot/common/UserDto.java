package com.parkin.parkinglot.common;

public class UserDto {
    Long id;
    String Email;
    String Username;
    String password;

    public UserDto(Long id, String username, String email, String password) {
        this.id = id;
        this.Email = email;
        this.Username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return Email;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }
}
