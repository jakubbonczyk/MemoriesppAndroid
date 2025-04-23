package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String name;
    private String surname;
    private String login;
    private String password;
    private User.Role role;
    private Integer groupId;
}