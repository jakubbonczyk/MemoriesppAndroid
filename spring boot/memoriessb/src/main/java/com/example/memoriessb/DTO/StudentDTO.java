package com.example.memoriessb.DTO;

/**
 * Obiekt DTO reprezentujący podstawowe informacje o uczniu.
 * Zawiera identyfikator, imię i nazwisko ucznia.
 *
 * @param id       identyfikator ucznia
 * @param name     imię ucznia
 * @param surname  nazwisko ucznia
 */
public record StudentDTO(Integer id, String name, String surname) {

    /** Zwraca identyfikator ucznia. */
    public Integer getId() {
        return id;
    }

    /** Zwraca imię ucznia. */
    public String getName() {
        return name;
    }

    /** Zwraca nazwisko ucznia. */
    public String getSurname() {
        return surname;
    }
}
