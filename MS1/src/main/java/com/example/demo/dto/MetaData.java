package com.example.demo.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class MetaData {
    
  private int cycles;
  private int stocks;
  private LocalTime startTime;
  private LocalTime endTime;
  private int symbolId;
  private String interval;
  private int strategyId;
  private float targetPercent;
  private float stopLoss;
}
