package com.example.demo.dto;

import java.util.ArrayList;

import org.hibernate.mapping.List;

import lombok.Data;

@Data

public class MaDto {
    ArrayList<MaBaseDto> avgs;
}
