package com.mitienda.product_service.repository;

import com.mitienda.product_service.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    
    List<Pack> findByStockGreaterThan(Integer stock);
    
}