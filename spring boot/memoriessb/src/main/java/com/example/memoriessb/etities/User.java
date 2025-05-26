package com.example.memoriessb.etities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Encja reprezentująca użytkownika systemu.
 * Może pełnić rolę ucznia, nauczyciela lub administratora.
 * Zawiera podstawowe dane osobowe, zdjęcie oraz relacje do ocen, grup i danych logowania.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Enum reprezentujący rolę użytkownika:
     * T – nauczyciel, A – administrator, S – uczeń.
     */
    public enum Role {
        T, A, S
    }

    /** Unikalny identyfikator użytkownika. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusers")
    private Integer id;

    /** Imię użytkownika. */
    @Column(nullable = false)
    private String name;

    /** Nazwisko użytkownika. */
    @Column(nullable = false)
    private String surname;

    /** Rola użytkownika w systemie. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /** Zdjęcie profilowe użytkownika jako bajty (opcjonalnie). */
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    /** Oceny, które użytkownik otrzymał jako uczeń. */
    @OneToMany(mappedBy = "student")
    private List<Grade> receivedGrades;

    /** Oceny, które użytkownik wystawił jako nauczyciel. */
    @OneToMany(mappedBy = "teacher")
    private List<Grade> givenGrades;

    /** Wrażliwe dane logowania powiązane z użytkownikiem. */
    @OneToOne(mappedBy = "user")
    private SensitiveData sensitiveData;

    /** Lista przypisań użytkownika do grup (np. klas lub zespołów). */
    @OneToMany(mappedBy = "user")
    private List<GroupMember> groupMemberships;

    /** Klasa (przedmiot), do której przypisany jest uczeń (jeśli dotyczy). */
    @ManyToOne
    @JoinColumn(name = "class_idclass")
    private SchoolClass schoolClass;
}
