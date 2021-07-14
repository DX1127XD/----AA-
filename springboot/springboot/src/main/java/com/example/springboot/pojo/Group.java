package com.example.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private Integer id;
    private String place;
    private String time;
    private String events;
    private Integer consumption;
    private Integer actual;
    private Integer finish;
}
