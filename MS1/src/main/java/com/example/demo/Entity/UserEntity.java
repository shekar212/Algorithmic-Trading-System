package com.example.demo.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_entity")
public class UserEntity {
    @Getter
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
    
    @Getter
    @Setter
    @Column(name="fname")
	private String fname;
    @Getter
    @Setter
    @Column(name="lname")
	private String lname;

    @Getter
    @Setter
    @Column(name="password" ,nullable=false)
	private String password;

    @Getter
    @Setter
    @Column(name="email" ,nullable=false)
	private String email;

    

}