package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Encja reprezentująca przypisanie użytkownika do grupy.
 * Umożliwia powiązanie użytkownika z określoną grupą użytkowników.
 */
@Entity
@Table(name = "group_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    /** Unikalny identyfikator przypisania. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup_members")
    private Integer id;

    /** Grupa, do której należy użytkownik. */
    @ManyToOne
    @JoinColumn(name = "user_group_id", nullable = false)
    private UserGroup userGroup;

    /** Użytkownik przypisany do grupy. */
    @ManyToOne
    @JoinColumn(name = "users_idusers", nullable = false)
    private User user;
}
