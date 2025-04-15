package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="strategy_list")
public class StrategyEntity {
    @Getter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

    
    @Getter
    @Setter
    @Column(name="strategy_name")
	private String strategy;

    
    @Getter
    @Setter
    @Column(name="descp")
	private String descp;
}
