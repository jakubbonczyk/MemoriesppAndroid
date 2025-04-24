package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EditUserResponse {
    private Integer id;
    private String login;
    private String name;
    private String surname;
    private User.Role role;
}
