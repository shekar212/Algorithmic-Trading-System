package com.example.demo.Entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stock_orders")
public class OrderEntity {
    @Getter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;

    @Getter
    @Setter
    @Column(name="price")
    private double price; 

    @Getter
    @Setter
    @Column(name="order_type")
    private String type; 

    @Getter
    @Setter
    @Column(name="order_time")
    private Date time; 

    @Getter
    @Setter
    @ManyToOne
	@JoinColumn(name="meta_id")
	private MetaEntity meta; 

}
