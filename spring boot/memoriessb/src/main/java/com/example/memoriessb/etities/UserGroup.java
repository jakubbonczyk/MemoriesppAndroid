package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_group")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    // powiązanie z GroupMember (poprzednio group_members)
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<GroupMember> members;

    // powiązanie z Schedule
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL)
    private List<Schedule> schedules;
}
