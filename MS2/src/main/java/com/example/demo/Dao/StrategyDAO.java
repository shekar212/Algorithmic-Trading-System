package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.StrategyEntity;

@Repository
public interface StrategyDAO extends JpaRepository<StrategyEntity, Integer>{

}
