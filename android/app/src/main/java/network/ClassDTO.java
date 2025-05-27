package network;

/**
 * Model danych (DTO) reprezentujący klasę.
 * Używany do przesyłania podstawowych informacji o klasie między frontendem a backendem.
 */
public class ClassDTO {

    /** Unikalny identyfikator klasy. */
    private Integer id;

    /** Nazwa klasy. */
    private String className;

    /**
     * Konstruktor inicjalizujący obiekt klasy na podstawie identyfikatora i nazwy.
     *
     * @param id        identyfikator klasy
     * @param className nazwa klasy
     */
    public ClassDTO(Integer id, String className) {
        this.id = id;
        this.className = className;
    }

    /**
     * Zwraca identyfikator klasy.
     *
     * @return identyfikator klasy
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia identyfikator klasy.
     *
     * @param id identyfikator klasy
     */
    public void setId(Integer id) {
        this.id = id;
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
     * Ustawia nazwę klasy.
     *
     * @param className nazwa klasy
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
