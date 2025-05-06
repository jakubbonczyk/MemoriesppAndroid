package com.example.memoriessb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GradeSummaryDTO {
    private Integer id;
    private Integer grade;
    private String type;
    private String issueDate;

}
