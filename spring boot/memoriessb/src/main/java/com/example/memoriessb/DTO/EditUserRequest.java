package com.example.memoriessb.DTO;

import com.example.memoriessb.etities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditUserRequest {
    private String login;
    private String name;
    private String surname;
    private User.Role role;
}
