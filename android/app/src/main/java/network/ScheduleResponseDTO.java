package network;

import com.google.gson.annotations.SerializedName;

public class ScheduleResponseDTO {
    @SerializedName("id")
    private Integer id;

    @SerializedName("assignmentId")
    private Integer assignmentId;

    @SerializedName("lessonDate")
    private String lessonDate;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("teacherName")
    private String teacherName;

    @SerializedName("className")
    private String className;

    public Integer getId() {
        return id;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getClassName() {
        return className;
    }
}

