package network;

import com.google.gson.annotations.SerializedName;

public class AssignmentDTO {
    @SerializedName("assignmentId")
    private int assignmentId;

    @SerializedName("teacherName")
    private String teacherName;

    @SerializedName("classId")
    private int classId;

    @SerializedName("className")
    private String className;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
