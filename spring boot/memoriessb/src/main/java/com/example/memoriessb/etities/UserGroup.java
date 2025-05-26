package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Encja reprezentująca grupę użytkowników (np. klasę, zespół lub grupę nauczycieli).
 * Zawiera nazwę grupy oraz listę przypisanych członków.
 */
@Entity
@Table(name = "user_group")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserGroup {

    /** Unikalny identyfikator grupy. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nazwa grupy (np. "Grupa Matematyka 1", "Zespół A"). */
    @Column(name = "group_name", nullable = false)
    private String groupName;

    /** Lista członków przypisanych do tej grupy. */
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<GroupMember> members;
}
