package network;

import com.google.gson.annotations.SerializedName;

/**
 * Model odpowiedzi z serwera zawierający szczegółowe informacje o zaplanowanej lekcji.
 * Używany do wyświetlania planu zajęć dla nauczycieli i grup.
 */
public class ScheduleResponseDTO {

    /** Identyfikator lekcji. */
    @SerializedName("id")
    private Integer id;

    /** Identyfikator przypisania nauczyciela do przedmiotu i grupy. */
    @SerializedName("assignmentId")
    private Integer assignmentId;

    /** Data lekcji w formacie YYYY-MM-DD. */
    @SerializedName("lessonDate")
    private String lessonDate;

    /** Godzina rozpoczęcia lekcji w formacie HH:mm:ss. */
    @SerializedName("startTime")
    private String startTime;

    /** Godzina zakończenia lekcji w formacie HH:mm:ss. */
    @SerializedName("endTime")
    private String endTime;

    /** Imię i nazwisko nauczyciela prowadzącego lekcję. */
    @SerializedName("teacherName")
    private String teacherName;

    /** Nazwa przedmiotu realizowanego na lekcji. */
    @SerializedName("subjectName")
    private String subjectName;

    /** Nazwa grupy uczestniczącej w lekcji. */
    @SerializedName("groupName")
    private String groupName;

    /**
     * Tworzy obiekt zawierający dane planowanej lekcji.
     *
     * @param id            identyfikator lekcji
     * @param assignmentId  identyfikator przypisania
     * @param lessonDate    data lekcji
     * @param startTime     godzina rozpoczęcia
     * @param endTime       godzina zakończenia
     * @param teacherName   imię i nazwisko nauczyciela
     * @param subjectName   nazwa przedmiotu
     * @param groupName     nazwa grupy
     */
    public ScheduleResponseDTO(Integer id,
                               Integer assignmentId,
                               String lessonDate,
                               String startTime,
                               String endTime,
                               String teacherName,
                               String subjectName,
                               String groupName) {
        this.id            = id;
        this.assignmentId  = assignmentId;
        this.lessonDate    = lessonDate;
        this.startTime     = startTime;
        this.endTime       = endTime;
        this.teacherName   = teacherName;
        this.subjectName   = subjectName;
        this.groupName     = groupName;
    }

    /**
     * Zwraca identyfikator lekcji.
     *
     * @return identyfikator lekcji
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zwraca identyfikator przypisania nauczyciela.
     *
     * @return identyfikator przypisania
     */
    public Integer getAssignmentId() {
        return assignmentId;
    }

    /**
     * Zwraca datę lekcji.
     *
     * @return data lekcji
     */
    public String getLessonDate() {
        return lessonDate;
    }

    /**
     * Zwraca godzinę rozpoczęcia lekcji.
     *
     * @return godzina rozpoczęcia
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Zwraca godzinę zakończenia lekcji.
     *
     * @return godzina zakończenia
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Zwraca imię i nazwisko nauczyciela.
     *
     * @return nauczyciel prowadzący lekcję
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Zwraca nazwę przedmiotu.
     *
     * @return nazwa przedmiotu
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Ustawia nazwę przedmiotu.
     *
     * @param subjectName nazwa przedmiotu
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
