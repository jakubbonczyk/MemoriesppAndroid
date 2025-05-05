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

    @SerializedName("subjectName")      // musi być subjectName, nie className
    private String subjectName;

    @SerializedName("groupName")        // nowe pole
    private String groupName;

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

    public Integer getId()             { return id; }
    public Integer getAssignmentId()   { return assignmentId; }
    public String  getLessonDate()     { return lessonDate; }
    public String  getStartTime()      { return startTime; }
    public String  getEndTime()        { return endTime; }
    public String  getTeacherName()    { return teacherName; }
    public String  getSubjectName()    { return subjectName; }
    public String  getGroupName()      { return groupName; }

    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setGroupName  (String groupName)   { this.groupName   = groupName;   }
    // (opcjonalnie inne settery)
}
