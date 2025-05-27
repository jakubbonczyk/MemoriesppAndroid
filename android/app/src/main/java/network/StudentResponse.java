package network;

/**
 * Model odpowiedzi z serwera zawierający podstawowe informacje o uczniu.
 * Używany m.in. przy pobieraniu listy uczniów przypisanych do danej grupy.
 */
public class StudentResponse {

    /** Identyfikator ucznia. */
    private int id;

    /** Imię ucznia. */
    private String name;

    /** Nazwisko ucznia. */
    private String surname;

    /** Konstruktor bezargumentowy wymagany do deserializacji. */
    public StudentResponse() {}

    /**
     * Zwraca identyfikator ucznia.
     *
     * @return identyfikator ucznia
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia identyfikator ucznia.
     *
     * @param id identyfikator ucznia
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca imię ucznia.
     *
     * @return imię ucznia
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia imię ucznia.
     *
     * @param name imię ucznia
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zwraca nazwisko ucznia.
     *
     * @return nazwisko ucznia
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Ustawia nazwisko ucznia.
     *
     * @param surname nazwisko ucznia
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
