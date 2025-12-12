package com.mitienda.product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mitienda.product_service.model.Coffee;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
    // JPA, busca en tablas "products" + "coffees"
    // JPQL: usa nombre de entidad, no de la tabla fisica

    List<Coffee> findByOriginIgnoreCase(String origin);

    List<Coffee> findByProcessIgnoreCase(String process);

    List<Coffee> findByProcessInIgnoreCase(String process);

    List<Coffee> findByProcessNotInIgnoreCase(List<String> processes);

}
