package com.example.demo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderDTO {
    private int id;
    private double price;
    private String type;
    @JsonFormat(pattern = "MM-dd-yy HH:mm")
    private Date time;
}
