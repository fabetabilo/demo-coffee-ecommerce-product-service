package com.mitienda.product_service.dto.coffee;

import com.mitienda.product_service.model.CoffeeVariant;
import lombok.Data;

@Data
public class CreateCoffeeVariantDto {

    private Long id;
    private String label;
    private Integer grams;
    private Double price;  
    private Integer stock; // !! IMOPORTANTE: stock real del formato de cafe disponible (EJ: 3 bolsas de 250g)

    /**
     * Convierte el DTO de entrada en una entidad {@link CoffeeVariant} lista para persistir.
     * @return {@link CoffeeVariant} Variante o formato de cafe
     */
    public CoffeeVariant toEntity() {

        CoffeeVariant variant = new CoffeeVariant();

        variant.setId(this.id);
        variant.setLabel(this.label);
        variant.setGrams(this.grams);
        variant.setPrice(this.price);
        variant.setStock(this.stock);
        
        return variant;

    }
}