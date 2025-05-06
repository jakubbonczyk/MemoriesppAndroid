package com.example.memoriessb.DTO;

public class GradeDetailDTO {
    private String type;
    private int grade;
    private String description;
    private String teacherName;
    private String teacherSurname;

    public GradeDetailDTO(int id, String type, int grade, String description, String teacherName, String teacherSurname) {
        this.type = type;
        this.grade = grade;
        this.description = description;
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
    }

}
