package com.mitienda.product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitienda.product_service.model.Accessory;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    // JPA, busca en tablas "products" + "accessories"

    List<Accessory> findByBrand(String brand);

}
