package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public enum Role {
        T, A, S
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusers")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "student")
    private List<Grade> receivedGrades;

    @OneToMany(mappedBy = "teacher")
    private List<Grade> givenGrades;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules;

    @OneToOne(mappedBy = "user")
    private SensitiveData sensitiveData;

    @OneToMany(mappedBy = "user")
    private List<GroupMember> groupMemberships;
}

