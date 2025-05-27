package network;

import com.google.gson.annotations.SerializedName;


/**
 * Model danych (DTO) reprezentujący przypisanie nauczyciela do klasy.
 * Wykorzystywany do komunikacji z backendem przez API.
 */
public class AssignmentDTO {

    /** Unikalny identyfikator przypisania. */
    @SerializedName("assignmentId")
    private int assignmentId;

    /** Imię i nazwisko nauczyciela przypisanego do klasy. */
    @SerializedName("teacherName")
    private String teacherName;

    /** Identyfikator klasy, do której przypisano nauczyciela. */
    @SerializedName("classId")
    private int classId;

    /** Nazwa klasy, do której przypisano nauczyciela. */
    @SerializedName("className")
    private String className;

    /**
     * Zwraca identyfikator przypisania.
     *
     * @return identyfikator przypisania
     */
    public int getAssignmentId() {
        return assignmentId;
    }

    /**
     * Ustawia identyfikator przypisania.
     *
     * @param assignmentId identyfikator przypisania
     */
    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    /**
     * Zwraca imię i nazwisko nauczyciela.
     *
     * @return imię i nazwisko nauczyciela
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Ustawia imię i nazwisko nauczyciela.
     *
     * @param teacherName imię i nazwisko nauczyciela
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * Zwraca identyfikator klasy.
     *
     * @return identyfikator klasy
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Ustawia identyfikator klasy.
     *
     * @param classId identyfikator klasy
     */
    public void setClassId(int classId) {
        this.classId = classId;
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