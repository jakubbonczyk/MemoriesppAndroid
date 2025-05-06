package com.example.memoriessb.DTO;

import lombok.Getter;

@Getter
public class GradeSummaryDTO {
    private int id;
    private String type;
    private int grade;

    public GradeSummaryDTO(int id, String type, int grade) {
        this.id = id;
        this.type = type;
        this.grade = grade;
    }


}
