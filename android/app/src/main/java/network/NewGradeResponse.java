package network;

public class NewGradeResponse {
    private Integer id;
    private Integer grade;
    private String type;
    private String issueDate;
    private String className;

    public NewGradeResponse() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
}
