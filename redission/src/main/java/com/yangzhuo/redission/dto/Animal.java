package com.yangzhuo.redission.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Animal implements Serializable {

    private String aId;
    private String color;
    private String hobby;
    private String aName;
}
