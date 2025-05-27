package network;

import com.google.gson.annotations.SerializedName;

/**
 * Model żądania rejestracji nowego użytkownika.
 * Zawiera dane niezbędne do utworzenia konta użytkownika w systemie.
 */
public class RegisterUserRequest {

    /** Login użytkownika (adres e-mail lub nazwa użytkownika). */
    @SerializedName("login")
    private final String login;

    /** Hasło użytkownika. */
    @SerializedName("password")
    private final String password;

    /** Imię użytkownika. */
    @SerializedName("name")
    private final String name;

    /** Nazwisko użytkownika. */
    @SerializedName("surname")
    private final String surname;

    /** Rola użytkownika (np. "A" – administrator, "T" – nauczyciel, "S" – uczeń). */
    @SerializedName("role")
    private final String role;

    /** Identyfikator grupy, do której przypisany będzie użytkownik (może być null dla admina). */
    @SerializedName("groupId")
    private final Integer groupId;

    /**
     * Tworzy nowe żądanie rejestracji użytkownika z podanymi danymi.
     *
     * @param login    login użytkownika
     * @param password hasło użytkownika
     * @param name     imię użytkownika
     * @param surname  nazwisko użytkownika
     * @param role     rola użytkownika
     * @param groupId  identyfikator grupy (opcjonalny)
     */
    public RegisterUserRequest(String login,
                               String password,
                               String name,
                               String surname,
                               String role,
                               Integer groupId) {
        this.login    = login;
        this.password = password;
        this.name     = name;
        this.surname  = surname;
        this.role     = role;
        this.groupId  = groupId;
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
     * Zwraca hasło użytkownika.
     *
     * @return hasło użytkownika
     */
    public String getPassword() {
        return password;
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
     * Zwraca identyfikator grupy przypisanej do użytkownika.
     *
     * @return identyfikator grupy (może być null)
     */
    public Integer getGroupId() {
        return groupId;
    }
}
