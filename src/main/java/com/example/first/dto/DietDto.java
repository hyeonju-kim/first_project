package com.example.first.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class DietDto {
    private Integer seq;
    private String foodNameMorning;
    private String foodNameLunch;
    private String foodNameDinner;
    private String foodNameSnack;
    private double intakeCaloriesMorning;
    private double intakeCaloriesLunch;
    private double intakeCaloriesDinner;
    private double intakeCaloriesSnack;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // wow!!!!!
    private LocalDate intakeDate;

//    private String intakeType;
    private String intakeResult; // 적정, 부족, 과다
    private String username;
    private String nickname;
    private double intakeTotal;

    public DietDto() {

    }



    public DietDto(double intakeCaloriesMorning, double intakeCaloriesLunch, double intakeCaloriesDinner, LocalDate intakeDate, String intakeResult, String username) {
        this.intakeCaloriesMorning = intakeCaloriesMorning;
        this.intakeCaloriesLunch = intakeCaloriesLunch;
        this.intakeCaloriesDinner = intakeCaloriesDinner;
        this.username = username;
        this.intakeDate = intakeDate;
        this.intakeTotal = intakeCaloriesMorning + intakeCaloriesLunch + intakeCaloriesDinner;
        this.intakeResult = intakeResult;
    }

    public DietDto(String intakeResult, String nickname) {
        this.intakeResult = intakeResult;
        this.nickname = nickname;
    }

}
