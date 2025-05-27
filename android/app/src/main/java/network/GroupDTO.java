package network;

/**
 * Model danych (DTO) reprezentujący grupę użytkowników.
 * Używany do przesyłania podstawowych informacji o grupie między frontendem a backendem.
 */
public class GroupDTO {

    /** Unikalny identyfikator grupy. */
    private Integer id;

    /** Nazwa grupy. */
    private String groupName;

    /**
     * Tworzy nowy obiekt grupy z podanym identyfikatorem i nazwą.
     *
     * @param id        identyfikator grupy
     * @param groupName nazwa grupy
     */
    public GroupDTO(Integer id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    /**
     * Zwraca identyfikator grupy.
     *
     * @return identyfikator grupy
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia identyfikator grupy.
     *
     * @param id identyfikator grupy
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Zwraca nazwę grupy.
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
