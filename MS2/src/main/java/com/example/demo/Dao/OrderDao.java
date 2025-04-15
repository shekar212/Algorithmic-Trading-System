package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.MetaEntity;
import com.example.demo.Entity.OrderEntity;

@Repository
public interface OrderDao extends JpaRepository <OrderEntity, Integer>{

    List<OrderEntity> findByMeta(MetaEntity meta);

}
