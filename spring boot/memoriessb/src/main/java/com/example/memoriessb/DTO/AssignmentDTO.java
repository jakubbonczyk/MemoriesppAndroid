package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignmentDTO {
    private Integer assignmentId;
    private String teacherName;
    private Integer classId;
    private String className;
}
