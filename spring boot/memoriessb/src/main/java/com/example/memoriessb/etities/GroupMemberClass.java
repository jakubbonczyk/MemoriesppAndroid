package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group_members_has_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_members_idgroup_members", nullable = false)
    private GroupMember groupMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_idclass", nullable = false)
    private SchoolClass schoolClass;

    public GroupMemberClass(GroupMember gm, SchoolClass cls) {
        this.groupMember = gm;
        this.schoolClass = cls;
    }
}
