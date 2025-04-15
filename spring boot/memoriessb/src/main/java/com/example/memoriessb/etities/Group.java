package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgroups")
    private Integer id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "group")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> members;
}
