package network;

/**
 * Model odpowiedzi z serwera zawierający informacje o grupie przypisanej do nauczyciela.
 * Używany przy pobieraniu listy grup, w których nauczyciel prowadzi zajęcia.
 */
public class TeacherGroupResponse {

    /** Identyfikator grupy. */
    private int id;

    /** Nazwa grupy. */
    private String groupName;

    /**
     * Zwraca identyfikator grupy.
     *
     * @return identyfikator grupy
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca nazwę grupy.
     *
     * @return nazwa grupy
     */
    public String getGroupName() {
        return groupName;
    }
}
