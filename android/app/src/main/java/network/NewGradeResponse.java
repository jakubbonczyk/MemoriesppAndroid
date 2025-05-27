package network;

/**
 * Model odpowiedzi zawierający informacje o nowej (nieprzeczytanej) ocenie ucznia.
 * Używany do powiadamiania ucznia o nowych ocenach po zalogowaniu.
 */
public class NewGradeResponse {

    /** Identyfikator oceny. */
    private Integer id;

    /** Wartość oceny. */
    private Integer grade;

    /** Typ oceny (np. sprawdzian, odpowiedź, projekt). */
    private String type;

    /** Data wystawienia oceny. */
    private String issueDate;

    /** Nazwa klasy, w której wystawiono ocenę. */
    private String className;

    /** Konstruktor bezargumentowy wymagany do deserializacji. */
    public NewGradeResponse() {}

    /**
     * Zwraca identyfikator oceny.
     *
     * @return identyfikator oceny
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia identyfikator oceny.
     *
     * @param id identyfikator oceny
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Zwraca wartość oceny.
     *
     * @return wartość oceny
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * Ustawia wartość oceny.
     *
     * @param grade wartość oceny
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * Zwraca typ oceny.
     *
     * @return typ oceny
     */
    public String getType() {
        return type;
    }

    /**
     * Ustawia typ oceny.
     *
     * @param type typ oceny
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Zwraca datę wystawienia oceny.
     *
     * @return data wystawienia
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * Ustawia datę wystawienia oceny.
     *
     * @param issueDate data wystawienia
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Zwraca nazwę klasy, w której wystawiono ocenę.
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }

    /**
     * Ustawia nazwę klasy, w której wystawiono ocenę.
     *
     * @param className nazwa klasy
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
