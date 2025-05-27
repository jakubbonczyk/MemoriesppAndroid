package network;

/**
 * Model żądania edycji danych użytkownika.
 * Wykorzystywany do przesyłania zmienionych danych użytkownika do serwera.
 */
public class EditUserRequest {

    /** Login użytkownika (adres e-mail lub nazwa użytkownika). */
    private String login;

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Rola użytkownika (np. "A" – administrator, "T" – nauczyciel, "S" – uczeń). */
    private String role;

    /**
     * Tworzy nowe żądanie edycji danych użytkownika.
     *
     * @param login   login użytkownika
     * @param name    imię użytkownika
     * @param surname nazwisko użytkownika
     * @param role    rola użytkownika
     */
    public EditUserRequest(String login, String name, String surname, String role) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.role = role;
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
     * Zwraca login użytkownika.
     *
     * @return login użytkownika
     */
    public String getLogin() {
        return login;
    }

    /**
     * Ustawia login użytkownika.
     *
     * @param login login użytkownika
     */
    public void setLogin(String login) {
        this.login = login;
    }
}
