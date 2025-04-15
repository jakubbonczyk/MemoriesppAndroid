package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sensitive_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsensitive_data")
    private Integer id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false, length = 256)
    private String password;

    @OneToOne
    @JoinColumn(name = "users_idusers", nullable = false)
    private User user;
}
