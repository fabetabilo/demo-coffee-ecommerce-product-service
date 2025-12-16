package com.mitienda.product_service.dto.coffee;

import com.mitienda.product_service.model.CoffeeVariant;
import lombok.Data;

@Data
public class AdminCoffeeVariantDto {

    private Long id;
    private String label; 
    private Integer grams;
    private Double price;
    private boolean available; 

    private Integer stock; 

    /**
     * Convierte una entidad {@link CoffeeVariant} a su DTO para administracion.
     * <p>
     * Incluye los campos de identificacion y presentacion (id, label, grams, price),
     * la disponibilidad calculada via {@link CoffeeVariant#getAvailable()} y el
     * {@code stock} real para monitoreo operativo.
     * @param variant {@link CoffeeVariant} variante persistente.
     * @return {@link AdminCoffeeVariantDto} con datos completos para la consola admin.
     */
    public static AdminCoffeeVariantDto fromEntity(CoffeeVariant variant) {

        AdminCoffeeVariantDto dto = new AdminCoffeeVariantDto();
        
        dto.setId(variant.getId());
        dto.setLabel(variant.getLabel());
        dto.setGrams(variant.getGrams());
        dto.setPrice(variant.getPrice());
        dto.setAvailable(variant.getAvailable());
        dto.setStock(variant.getStock());  // para mostrar el stock real
        
        return dto;
    }
}