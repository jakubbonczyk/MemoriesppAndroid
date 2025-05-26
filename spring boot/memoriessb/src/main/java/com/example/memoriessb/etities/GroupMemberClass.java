package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Encja reprezentująca przypisanie nauczyciela (członka grupy) do konkretnego przedmiotu (klasy).
 * Służy do odwzorowania relacji wiele-do-wielu między grupami nauczycieli a przedmiotami.
 */
@Entity
@Table(name = "group_members_has_class")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberClass {

    /** Unikalny identyfikator przypisania. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Przypisany członek grupy (np. nauczyciel). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_members_idgroup_members", nullable = false)
    private GroupMember groupMember;

    /** Przypisana klasa (przedmiot). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_idclass", nullable = false)
    private SchoolClass schoolClass;

    /**
     * Konstruktor tworzący przypisanie na podstawie członka grupy i klasy.
     *
     * @param gm   obiekt GroupMember
     * @param cls  obiekt SchoolClass
     */
    public GroupMemberClass(GroupMember gm, SchoolClass cls) {
        this.groupMember = gm;
        this.schoolClass = cls;
    }
}
