package com.yangzhuo.design.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Scores {
    private String name;
    private int age;
    private String sex;
    private String subject;
    private int score;
}