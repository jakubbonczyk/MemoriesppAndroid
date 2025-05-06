package network;

public class GradeDetailDTO {
    private Integer id;
    private Integer grade;
    private String type;

    private String issueDate;

    private String description;
    private String teacherName;
    private String className;
    private String studentName;

    public GradeDetailDTO() {}

    public Integer getId() {
        return id;
    }
    public Integer getGrade() {
        return grade;
    }
    public String getType() {
        return type;
    }
    public String getIssueDate() {
        return issueDate;
    }
    public String getDescription() {
        return description;
    }
    public String getTeacherName() {
        return teacherName;
    }
    public String getClassName() {
        return className;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setGrade(Integer grade) {
        this.grade = grade;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
