package network;

/**
 * Model użytkownika systemu.
 * Zawiera podstawowe informacje takie jak imię, nazwisko, rola oraz identyfikator.
 */
public class User {

    /** Unikalny identyfikator użytkownika. */
    private int id;

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Rola użytkownika (np. "A" – administrator, "T" – nauczyciel, "S" – uczeń). */
    private String role;

    /**
     * Zwraca pełne imię i nazwisko użytkownika w formacie "Imię Nazwisko".
     *
     * @return pełne imię i nazwisko użytkownika
     */
    public String getFullName() {
        return name + " " + surname;
    }

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return identyfikator użytkownika
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param id identyfikator użytkownika
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca imię użytkownika.
     *
     * @return imię użytkownika
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia imię użytkownika.
     *
     * @param name imię użytkownika
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zwraca nazwisko użytkownika.
     *
     * @return nazwisko użytkownika
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Ustawia nazwisko użytkownika.
     *
     * @param surname nazwisko użytkownika
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Zwraca rolę użytkownika.
     *
     * @return rola użytkownika
     */
    public String getRole() {
        return role;
    }

    /**
     * Ustawia rolę użytkownika.
     *
     * @param role rola użytkownika
     */
    public void setRole(String role) {
        this.role = role;
    }
}
