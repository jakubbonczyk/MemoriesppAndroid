package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroup_members")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "groups_idgroups", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "users_idusers", nullable = false)
    private User user;
}
