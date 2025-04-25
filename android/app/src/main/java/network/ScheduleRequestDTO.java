package network;

import com.google.gson.annotations.SerializedName;

public class ScheduleRequestDTO {
    @SerializedName("assignmentId")
    private int assignmentId;

    @SerializedName("lessonDate")
    private String lessonDate; // format YYYY-MM-DD

    @SerializedName("startTime")
    private String startTime;  // format HH:mm:ss

    @SerializedName("endTime")
    private String endTime;    // format HH:mm:ss

    public ScheduleRequestDTO(int assignmentId, String lessonDate, String startTime, String endTime) {
        this.assignmentId = assignmentId;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}