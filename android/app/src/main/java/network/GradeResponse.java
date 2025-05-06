package network;

public class GradeResponse {
    private Integer id;
    private Integer grade;
    private String description;
    private String type;
    private String issueDate;
    private String teacherName;

    public Integer getId() { return id; }
    public Integer getGrade() { return grade; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getIssueDate() { return issueDate; }
    public String getTeacherName() { return teacherName; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}


