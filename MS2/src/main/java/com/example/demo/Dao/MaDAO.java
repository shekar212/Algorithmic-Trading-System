package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MaEntity;
import com.example.demo.Entity.MetaEntity;

@Repository
public interface MaDAO extends JpaRepository<MaEntity, Integer>{

    List<MaEntity> findByMeta(MetaEntity entity);

}
