package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.UserEntity;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Integer> {
	
	UserEntity findByEmail(String email);
	
	UserEntity findByEmailAndPasswordNotNull(String email);
	//select from employee where email='...' and password!=null

}
