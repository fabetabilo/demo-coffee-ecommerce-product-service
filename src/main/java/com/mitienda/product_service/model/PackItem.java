package com.mitienda.product_service.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackItem {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String label;   // "80g, 100g"
    
    @Column(nullable = false)
    private Integer grams; // ej: 80, 100 (gramos)

    @Column(nullable = false)
    private String process;

    @Column(name = "original_coffee_id", nullable = true)
    private Long originalCoffeeId;  // IMPORTANTE REFERENCIAL: el item, puede o no tener el id de un cafe original de Coffee.

}