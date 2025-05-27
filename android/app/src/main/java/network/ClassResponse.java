package network;

import com.google.gson.annotations.SerializedName;

/**
 * Model odpowiedzi z serwera zawierający dane klasy.
 * Używany do prezentowania informacji o klasach, w tym ich średniej ocen.
 */
public class ClassResponse {

    /** Unikalny identyfikator klasy. */
    private Integer id;

    /** Nazwa klasy. */
    private String className;

    /** Średnia ocen uczniów w danej klasie. */
    @SerializedName("average")
    private Double average;

    /**
     * Zwraca średnią ocen dla klasy.
     *
     * @return średnia ocen
     */
    public Double getAverage() {
        return average;
    }

    /**
     * Ustawia średnią ocen dla klasy.
     *
     * @param average średnia ocen
     */
    public void setAverage(Double average) {
        this.average = average;
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
