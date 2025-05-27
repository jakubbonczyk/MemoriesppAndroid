package network;

import com.google.gson.annotations.SerializedName;

/**
 * Model żądania utworzenia nowej lekcji w planie zajęć.
 * Zawiera informacje o przypisaniu nauczyciela, dacie oraz godzinach rozpoczęcia i zakończenia.
 */
public class ScheduleRequestDTO {

    /** Identyfikator przypisania nauczyciela do klasy i przedmiotu. */
    @SerializedName("assignmentId")
    private int assignmentId;

    /** Data lekcji w formacie YYYY-MM-DD. */
    @SerializedName("lessonDate")
    private String lessonDate;

    /** Godzina rozpoczęcia lekcji w formacie HH:mm:ss. */
    @SerializedName("startTime")
    private String startTime;

    /** Godzina zakończenia lekcji w formacie HH:mm:ss. */
    @SerializedName("endTime")
    private String endTime;

    /**
     * Tworzy obiekt żądania dodania lekcji.
     *
     * @param assignmentId identyfikator przypisania nauczyciela
     * @param lessonDate   data lekcji
     * @param startTime    godzina rozpoczęcia lekcji
     * @param endTime      godzina zakończenia lekcji
     */
    public ScheduleRequestDTO(int assignmentId, String lessonDate, String startTime, String endTime) {
        this.assignmentId = assignmentId;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Zwraca identyfikator przypisania nauczyciela.
     *
     * @return identyfikator przypisania
     */
    public int getAssignmentId() {
        return assignmentId;
    }

    /**
     * Ustawia identyfikator przypisania nauczyciela.
     *
     * @param assignmentId identyfikator przypisania
     */
    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
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
     * Ustawia datę lekcji.
     *
     * @param lessonDate data lekcji
     */
    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
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
     * Ustawia godzinę rozpoczęcia lekcji.
     *
     * @param startTime godzina rozpoczęcia
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
     * Ustawia godzinę zakończenia lekcji.
     *
     * @param endTime godzina zakończenia
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}