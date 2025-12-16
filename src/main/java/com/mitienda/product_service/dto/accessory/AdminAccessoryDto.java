package com.mitienda.product_service.dto.accessory;

import com.mitienda.product_service.model.Accessory;
import lombok.Data;
import java.util.List;

@Data
public class AdminAccessoryDto {

    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    private boolean available; // Útil para ver estado rápido en la tabla

    private Integer stock; // admin necesita ver estock
    private String brand;
    private List<String> features; 

    /**
     * Convierte una entidad {@link Accessory} a su representación DTO.
     * <p>
     * Acción de administrador con todos los campos necesarios.
     * @param accessory Producto {@link Accessory}
     * @return {@link AdminAccessoryDto} ; campos mapeados con stock.
     */
    public static AdminAccessoryDto fromEntity(Accessory accessory) {
        
        AdminAccessoryDto dto = new AdminAccessoryDto();
        
        dto.setId(accessory.getId());
        dto.setName(accessory.getName());
        dto.setDescription(accessory.getDescription());
        dto.setAdditionalDescription(accessory.getAdditionalDescription());
        dto.setPrice(accessory.getPrice());
        dto.setCategory(accessory.getCategory());
        dto.setSubcategory(accessory.getSubcategory());
        dto.setProductImages(accessory.getProductImages());
        dto.setAvailable(accessory.getAvailable());
    
        dto.setStock(accessory.getStock()); 
        dto.setBrand(accessory.getBrand());
        dto.setFeatures(accessory.getFeatures());

        return dto;
    }
}