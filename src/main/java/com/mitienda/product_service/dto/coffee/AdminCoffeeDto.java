package com.mitienda.product_service.dto.coffee;

import com.mitienda.product_service.model.Coffee;
import lombok.Data;
import java.util.List;

@Data
public class AdminCoffeeDto {
    
    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    private boolean available;

    private String origin;
    private String process;
    private Integer roastLevel;
    private List<String> descriptors;
    
    private List<AdminCoffeeVariantDto> formats; // lista de variantes de admin (las que tienen stock)

    /**
     * Convierte una entidad {@link Coffee} a su DTO para administracion.
     * <p>
     * Mapea todos los campos heredados (id, name, description, additionalDescription,
     * price, category, subcategory, productImages, available) y los atributos
     * especificos del cafe (origin, process, roastLevel, descriptors).
     * Ademas transforma las variantes con {@link AdminCoffeeVariantDto#fromEntity}
     * para exponer precios, stock y disponibilidad por formato.
     * @param coffee {@link Coffee} entidad persistente con variantes cargadas.
     * @return {@link AdminCoffeeDto} completo para vistas administrativas.
     */
    public static AdminCoffeeDto fromEntity(Coffee coffee) {
        
        AdminCoffeeDto dto = new AdminCoffeeDto();
        
        dto.setId(coffee.getId());
        dto.setName(coffee.getName());
        dto.setDescription(coffee.getDescription());
        dto.setAdditionalDescription(coffee.getAdditionalDescription());
        dto.setPrice(coffee.getPrice());
        dto.setCategory(coffee.getCategory());
        dto.setSubcategory(coffee.getSubcategory());
        dto.setProductImages(coffee.getProductImages());
        dto.setAvailable(coffee.getAvailable());

        dto.setOrigin(coffee.getOrigin());
        dto.setProcess(coffee.getProcess());
        dto.setRoastLevel(coffee.getRoastLevel());
        dto.setDescriptors(coffee.getDescriptors());

        if (coffee.getFormats() != null) {
            List<AdminCoffeeVariantDto> variants = coffee.getFormats().stream()
                .map(AdminCoffeeVariantDto::fromEntity)
                .toList();
            dto.setFormats(variants);
        }
        return dto;
    }
}