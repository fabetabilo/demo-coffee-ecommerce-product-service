package com.mitienda.product_service.dto;

import com.mitienda.product_service.model.CoffeeVariant;
import lombok.Data;

@Data
public class CoffeeVariantDto {

    private Long id;
    private String label; 
    private Integer grams;
    private Double price;
    private boolean available; 

    /**
     * Convierte una entidad {@link CoffeeVariant} a representación DTO.
     * <p>
     * Utiliza método {@code getAvailable()} para exponer un boolean según su stock.
     * @param variant {@link CoffeeVariant} Entidad persistente en base de datos.
     * @return DTO; label, precio, gramos, disponibilidad
     */
    public static CoffeeVariantDto fromEntity(CoffeeVariant variant) {
        
        CoffeeVariantDto dto = new CoffeeVariantDto();
        
        dto.setId(variant.getId());
        dto.setLabel(variant.getLabel());
        dto.setGrams(variant.getGrams());
        dto.setPrice(variant.getPrice());
        dto.setAvailable(variant.getAvailable()); // obtiene disponibilidad
        
        return dto;

    }
}