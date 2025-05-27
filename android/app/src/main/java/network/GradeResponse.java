package network;

/**
 * Model odpowiedzi z serwera zawierający informacje o ocenie ucznia.
 * Używany do prezentowania ocen w widokach listy ocen przedmiotowych.
 */
public class GradeResponse {

    /** Identyfikator oceny. */
    private Integer id;

    /** Wartość oceny (np. 1–6). */
    private Integer grade;

    /** Opis lub komentarz do oceny. */
    private String description;

    /** Typ oceny (np. sprawdzian, odpowiedź, aktywność). */
    private String type;

    /** Data wystawienia oceny. */
    private String issueDate;

    /** Imię i nazwisko nauczyciela wystawiającego ocenę. */
    private String teacherName;

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
     * Zwraca opis oceny.
     *
     * @return opis oceny
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ustawia opis oceny.
     *
     * @param description opis oceny
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Zwraca typ oceny (np. sprawdzian, odpowiedź).
     *
     * @return typ oceny
     */
    public String getType() {
        return type;
    }

    /**
     * Ustawia typ oceny (np. sprawdzian, odpowiedź).
     *
     * @param type typ oceny
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Zwraca datę wystawienia oceny.
     *
     * @return data wystawienia oceny
     */
    public String getIssueDate() {
        return issueDate;
    }

    /**
     * Ustawia datę wystawienia oceny.
     *
     * @param issueDate data wystawienia oceny
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Zwraca imię i nazwisko nauczyciela wystawiającego ocenę.
     *
     * @return imię i nazwisko nauczyciela
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Ustawia imię i nazwisko nauczyciela wystawiającego ocenę.
     *
     * @param teacherName imię i nazwisko nauczyciela
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
