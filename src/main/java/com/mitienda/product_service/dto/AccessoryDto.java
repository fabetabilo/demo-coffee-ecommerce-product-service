package com.mitienda.product_service.dto;

import com.mitienda.product_service.model.Accessory;
import lombok.Data;
import java.util.List;

@Data
public class AccessoryDto {

    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    private boolean available; // global calculado

    // campos propios
    private String brand;
    private List<String> features; 

    /**
     * Convierte una entidad {@link Accessory} a su representación DTO.
     * <p>
     * Mapea los campos de la superclase (id, name, description, additionalDescription,
     * price, category, subcategory, productImages) y los campos específicos de accesorio
     * (`brand`, `features`).
     * Utiliza el método {@code getAvailable()} para exponer la disponibilidad como boolean.
     * @param accessory {@link Accessory} Entidad persistente en base de datos.
     * @return DTO con campos mapeados: id, name, description, additionalDescription,
     * price, category, subcategory, productImages, available, brand, features
     */
    public static AccessoryDto fromEntity(Accessory accessory) {
        
        AccessoryDto dto = new AccessoryDto();
        
        // mapeo superclase
        dto.setId(accessory.getId());
        dto.setName(accessory.getName());
        dto.setDescription(accessory.getDescription());
        dto.setAdditionalDescription(accessory.getAdditionalDescription());
        dto.setPrice(accessory.getPrice());
        dto.setCategory(accessory.getCategory());
        dto.setSubcategory(accessory.getSubcategory());
        dto.setProductImages(accessory.getProductImages());
        dto.setAvailable(accessory.getAvailable());  // calculado por Accessory

        // campos propios
        dto.setBrand(accessory.getBrand());
        dto.setFeatures(accessory.getFeatures());

        return dto;

    }
}