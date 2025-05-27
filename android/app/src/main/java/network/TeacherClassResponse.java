package network;

/**
 * Model odpowiedzi z serwera zawierający informacje o klasie przypisanej do nauczyciela.
 * Używany przy pobieraniu listy klas, w których nauczyciel prowadzi zajęcia.
 */
public class TeacherClassResponse {

    /** Identyfikator klasy. */
    private int id;

    /** Nazwa klasy. */
    private String className;

    /**
     * Tworzy obiekt reprezentujący klasę przypisaną do nauczyciela.
     *
     * @param id        identyfikator klasy
     * @param className nazwa klasy
     */
    public TeacherClassResponse(int id, String className) {
        this.id = id;
        this.className = className;
    }

    /**
     * Zwraca identyfikator klasy.
     *
     * @return identyfikator klasy
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca nazwę klasy.
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }
}
