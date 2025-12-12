package com.mitienda.product_service.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mitienda.product_service.model.Coffee;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
    // JPA, busca en tablas "products" + "coffees"
    // JPQL: usa nombre de entidad, no de la tabla fisica

    List<Coffee> findByOriginIgnoreCase(String origin);

    List<Coffee> findByProcessIgnoreCase(String process);

    @Query("SELECT c FROM Coffee c WHERE LOWER(c.process) IN :processes")
    List<Coffee> findByProcessInIgnoreCase(@Param("processes") Collection<String> processes);

    @Query("SELECT c FROM Coffee c WHERE c.process IS NULL OR LOWER(c.process) NOT IN :standard")
    List<Coffee> findByProcessNotInIgnoreCase(@Param("standard") Collection<String> standard);

}
