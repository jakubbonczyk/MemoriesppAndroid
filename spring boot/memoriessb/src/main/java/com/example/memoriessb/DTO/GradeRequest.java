package com.example.memoriessb.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GradeRequest {
    private Integer studentId;
    private Integer teacherId;
    private Integer classId;
    private Integer grade;
    private String description;
    private String type;
}
