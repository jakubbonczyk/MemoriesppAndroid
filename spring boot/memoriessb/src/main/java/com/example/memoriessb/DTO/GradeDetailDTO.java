package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GradeDetailDTO {
    private Integer id;
    private Integer grade;
    private String type;
    private String issueDate;
    private String description;
    private String studentName;
    private String teacherName;
    private String className;


}
