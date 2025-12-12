package com.mitienda.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitienda.product_service.model.CoffeeVariant;

@Repository
public interface CoffeeVariantRepository extends JpaRepository<CoffeeVariant, Long> {

    // findById(Long id) : para validar el producto con variante
    
}
