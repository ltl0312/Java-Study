package com.itheima.demo9test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Movie {
    private String name;
    private String actor;
    private double score;
    private double price;
}
