package network;

/**
 * Model danych (DTO) zawierający szczegółowe informacje o ocenie.
 * Używany do wyświetlania pełnych danych o konkretnej ocenie ucznia.
 */
public class GradeDetailDTO {

    /** Identyfikator oceny. */
    private Integer id;

    /** Wartość oceny (np. 1–6). */
    private Integer grade;

    /** Typ oceny (np. sprawdzian, odpowiedź ustna, projekt). */
    private String type;

    /** Data wystawienia oceny. */
    private String issueDate;

    /** Opis lub komentarz nauczyciela do oceny. */
    private String description;

    /** Imię i nazwisko nauczyciela wystawiającego ocenę. */
    private String teacherName;

    /** Nazwa klasy, do której należy uczeń. */
    private String className;

    /** Imię i nazwisko ucznia. */
    private String studentName;

    /** Konstruktor bezargumentowy wymagany do deserializacji. */
    public GradeDetailDTO() {}

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
     * Zwraca opis lub komentarz do oceny.
     *
     * @return opis oceny
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ustawia opis lub komentarz do oceny.
     *
     * @param description opis oceny
     */
    public void setDescription(String description) {
        this.description = description;
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

    /**
     * Zwraca nazwę klasy, do której przypisany jest uczeń.
     *
     * @return nazwa klasy
     */
    public String getClassName() {
        return className;
    }

    /**
     * Ustawia nazwę klasy, do której przypisany jest uczeń.
     *
     * @param className nazwa klasy
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Zwraca imię i nazwisko ucznia.
     *
     * @return imię i nazwisko ucznia
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Ustawia imię i nazwisko ucznia.
     *
     * @param studentName imię i nazwisko ucznia
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
