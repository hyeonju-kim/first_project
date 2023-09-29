package com.example.first.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DietDto {
    private Long seq;
    private String foodName;
    private Double intakeCaloriesMorning;
    private Double intakeCaloriesLunch;
    private Double intakeCaloriesDinner;
    private Double intakeCaloriesSnack;
    private LocalDate intakeDate = LocalDate.now();
//    private String intakeType;
    private String intakeResult; // 적정, 부족, 과다
    private String username;



    public DietDto(Double intakeCaloriesMorning, Double intakeCaloriesLunch, Double intakeCaloriesDinner) {
        this.intakeCaloriesMorning = intakeCaloriesMorning;
        this.intakeCaloriesLunch = intakeCaloriesLunch;
        this.intakeCaloriesDinner = intakeCaloriesDinner;
    }
}
