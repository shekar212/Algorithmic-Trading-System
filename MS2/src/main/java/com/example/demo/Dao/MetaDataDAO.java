package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MetaEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.dto.Generic1;

@Repository
public interface MetaDataDAO extends JpaRepository<MetaEntity,Integer> {

	List<MetaEntity> findByUserAndStatus(UserEntity findByEmail, String string);

    List<MetaEntity> findByStatus(String string);

}
