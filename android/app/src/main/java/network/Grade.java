package network;

/**
 * Model reprezentujący ocenę wystawioną uczniowi.
 * Zawiera informacje o wartości oceny, jej opisie oraz nauczycielu, który ją wystawił.
 */
public class Grade {

    /** Unikalny identyfikator oceny. */
    private int id;

    /** Wartość oceny (np. 1–6). */
    private int grade;

    /** Opis lub uzasadnienie oceny. */
    private String description;

    /** Imię i nazwisko nauczyciela, który wystawił ocenę. */
    private String teacherName;

    /**
     * Zwraca identyfikator oceny.
     *
     * @return identyfikator oceny
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia identyfikator oceny.
     *
     * @param id identyfikator oceny
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca wartość oceny.
     *
     * @return wartość oceny
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Ustawia wartość oceny.
     *
     * @param grade wartość oceny
     */
    public void setGrade(int grade) {
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
     * Zwraca imię i nazwisko nauczyciela.
     *
     * @return nauczyciel wystawiający ocenę
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Ustawia imię i nazwisko nauczyciela.
     *
     * @param teacherName nauczyciel wystawiający ocenę
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
