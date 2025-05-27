package network;

/**
 * Model odpowiedzi z serwera zawierający informacje o przypisaniu nauczyciela do klasy.
 * Wykorzystywany przy pobieraniu danych z API.
 */
public class AssignmentResponse {

    /** Identyfikator klasy. */
    private int classId;

    /** Nazwa klasy. */
    private String className;

    /** Imię i nazwisko nauczyciela przypisanego do klasy. */
    private String teacherName;

    /**
     * Zwraca identyfikator klasy.
     *
     * @return identyfikator klasy
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Zwraca nazwę klasy.
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }

    /**
     * Zwraca imię i nazwisko nauczyciela.
     *
     * @return imię i nazwisko nauczyciela
     */
    public String getTeacherName() {
        return teacherName;
    }
}
