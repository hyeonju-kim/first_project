package com.example.first.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DietDto {
    private Integer seq;
    private String foodNameMorning;
    private String foodNameLunch;
    private String foodNameDinner;
    private String foodNameSnack;
    private Integer intakeCaloriesMorning;
    private Integer intakeCaloriesLunch;
    private Integer intakeCaloriesDinner;
    private Integer intakeCaloriesSnack;
    private LocalDate intakeDate = LocalDate.now();
//    private String intakeType;
    private String intakeResult; // 적정, 부족, 과다
    private String username;
    private Integer intakeTotal;



    public DietDto(Integer intakeCaloriesMorning, Integer intakeCaloriesLunch, Integer intakeCaloriesDinner, String intakeResult, String username) {
        this.intakeCaloriesMorning = intakeCaloriesMorning;
        this.intakeCaloriesLunch = intakeCaloriesLunch;
        this.intakeCaloriesDinner = intakeCaloriesDinner;
        this.username = username;
        this.intakeDate = LocalDate.now();
        this.intakeTotal = intakeCaloriesMorning + intakeCaloriesLunch + intakeCaloriesDinner;
        this.intakeResult = intakeResult;
    }
}
