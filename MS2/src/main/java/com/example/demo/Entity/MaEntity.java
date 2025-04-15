package com.example.demo.Entity;

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
@Table(name = "ma_values")
public class MaEntity {
    @Getter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private int id;

    @Getter
    @Setter
    @ManyToOne
	@JoinColumn(name="meta_id")
	private MetaEntity meta; 

    @Getter
    @Setter
    @Column(name="av_value")
    private int avg=1;

    @Getter
    @Setter
    @Column(name="entry_cond")
    private String entryCond;

   
}
