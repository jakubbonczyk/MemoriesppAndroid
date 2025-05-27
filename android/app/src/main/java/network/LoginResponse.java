package network;

/**
 * Model odpowiedzi z serwera po poprawnym zalogowaniu użytkownika.
 * Zawiera podstawowe dane użytkownika niezbędne do wyświetlenia w aplikacji.
 */
public class LoginResponse {

    /** Identyfikator użytkownika. */
    private int id;

    /** Imię użytkownika. */
    private String name;

    /** Nazwisko użytkownika. */
    private String surname;

    /** Rola użytkownika (np. "A" – administrator, "T" – nauczyciel, "S" – uczeń). */
    private String role;

    /** Zdjęcie profilowe użytkownika zakodowane w formacie Base64. */
    private String image;

    /** Nazwa klasy przypisanej do ucznia (jeśli dotyczy). */
    private String className;

    /**
     * Zwraca identyfikator użytkownika.
     *
     * @return identyfikator użytkownika
     */
    public int getId() {
        return id;
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

    /**
     * Zwraca zdjęcie profilowe użytkownika w formacie Base64.
     *
     * @return zdjęcie profilowe użytkownika
     */
    public String getImage() {
        return image;
    }

    /**
     * Zwraca nazwę klasy, do której przypisany jest użytkownik (jeśli dotyczy).
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }
}
