package network;

/**
 * Model odpowiedzi z serwera zawierający podstawowe informacje o grupie.
 * Używany przy pobieraniu listy dostępnych grup w systemie.
 */
public class GroupResponse {

    /** Unikalny identyfikator grupy. */
    private Integer id;

    /** Nazwa grupy. */
    private String groupName;

    /**
     * Zwraca identyfikator grupy.
     *
     * @return identyfikator grupy
     */
    public Integer getId() {
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
