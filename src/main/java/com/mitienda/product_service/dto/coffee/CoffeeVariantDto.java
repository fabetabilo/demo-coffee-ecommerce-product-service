package com.mitienda.product_service.dto.coffee;

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
     * Convierte una entidad {@link CoffeeVariant} a su DTO publico.
     * <p>
     * Mapea los atributos de la variante (id, label, grams, price) y expone la
     * disponibilidad usando {@link CoffeeVariant#getAvailable()} para reflejar el stock.
     * @param variant {@link CoffeeVariant} variante persistida asociada a un cafe.
     * @return {@link CoffeeVariantDto} con label, gramos, precio y disponibilidad.
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