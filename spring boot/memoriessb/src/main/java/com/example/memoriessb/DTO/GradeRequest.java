package com.example.memoriessb.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeRequest {
    private int grade;
    private String description;
    private String type;
    private int studentId;
    private int teacherId;
    private int classId;
}
