package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeResponse {
    private int id;
    private int grade;
    private String description;
    private String teacherName;
}
