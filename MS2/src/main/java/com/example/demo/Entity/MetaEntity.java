package com.example.demo.Entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "strategy_metadata")
public class MetaEntity {
    
    @Getter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;

    @Getter
    @Setter
    @Column(name="cycles")
    private int cycles;

    @Getter
    @Setter
    @Column(name="no_stocks")
    private int stocks=1;

    @Getter
    @Setter
    @JsonFormat(pattern = "HH:mm")
    @Column(name="start_time")
    private LocalTime startTime;

    @Getter
    @Setter
    @JsonFormat(pattern = "HH:mm")
    @Column(name="end_time")
    private LocalTime endTime;

    @Getter
    @Setter
    @Column(name="interval_time")
    private String interval;

    @Getter
    @Setter
    @Column(name="status_msg")
    private String status;

    @Getter
    @Setter
    @ManyToOne
	@JoinColumn(name="strategy_id")
	private StrategyEntity strategy;

    @Getter
    @Setter
    @ManyToOne
	@JoinColumn(name="user_id")
	private UserEntity user;

    @Getter
    @Setter
    @Column(name="target_percent")
    private double targetPercent=3;

    @Getter
    @Setter
    @Column(name="stop_loss")
    private double stopLoss=1.5;

    @Getter
    @Setter
    @ManyToOne
	@JoinColumn(name="symbol_id")
	private SymbolEntity symbol;
    
}
