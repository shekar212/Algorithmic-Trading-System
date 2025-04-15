package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MaEntity;

@Repository
public interface MaDAO extends JpaRepository<MaEntity, Integer>{

}
