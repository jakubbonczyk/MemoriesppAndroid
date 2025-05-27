package network;

/**
 * Model odpowiedzi serwera po edycji danych użytkownika.
 * Zawiera zaktualizowane informacje o użytkowniku.
 */
public class EditUserResponse {

    /** Unikalny identyfikator użytkownika. */
    private Integer id;

    /** Login użytkownika (adres e-mail lub nazwa użytkownika). */
    private String login;

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Rola użytkownika (np. "A" – administrator, "T" – nauczyciel, "S" – uczeń). */
    private String role;

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return identyfikator użytkownika
     */
    public Integer getId() {
        return id;
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
     * Zwraca imię użytkownika.
     *
     * @return imię użytkownika
     */
    public String getName() {
        return name;
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
     * Zwraca rolę użytkownika.
     *
     * @return rola użytkownika
     */
    public String getRole() {
        return role;
    }
}
