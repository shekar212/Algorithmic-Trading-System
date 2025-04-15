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
@Table(name="companies_listed")
public class SymbolEntity {

    @Getter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;

    
    @Getter
    @Setter
    @Column(name="symbol")
    private String symbol;

    
    @Getter
    @Setter
    @Column(name="name")
    private String name;

}
