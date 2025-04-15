package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.SymbolEntity;

@Repository
public interface SymbolDAO extends JpaRepository<SymbolEntity, Integer>{

    List<SymbolEntity> findFirst10BySymbolStartingWithOrNameStartingWith(String term1,String term2);
    
}
