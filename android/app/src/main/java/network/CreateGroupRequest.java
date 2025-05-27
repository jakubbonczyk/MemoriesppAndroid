package network;

/**
 * Model żądania tworzenia nowej grupy.
 * Używany przy wysyłaniu danych do serwera w celu utworzenia grupy.
 */
public class CreateGroupRequest {

    /** Nazwa nowej grupy. */
    private String groupName;

    /**
     * Tworzy nowe żądanie utworzenia grupy.
     *
     * @param groupName nazwa grupy
     */
    public CreateGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Zwraca nazwę grupy do utworzenia.
     *
     * @return nazwa grupy
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Ustawia nazwę grupy.
     *
     * @param groupName nazwa grupy
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
