package com.mitienda.product_service.dto.coffee;

import com.mitienda.product_service.model.Coffee;
import lombok.Data;
import java.util.List;

@Data
public class CoffeeDto {
    
    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    private boolean available; // global de superclase

    // campos propios
    private String origin;
    private String process;
    private Integer roastLevel;
    private List<String> descriptors;
    
    private List<CoffeeVariantDto> formats;

    /**
     * Convierte una entidad {@link Coffee} a su representacion publica.
     * <p>
     * Mapea los campos heredados (id, name, description, additionalDescription,
     * price, category, subcategory, productImages) y expone {@code available}
     * segun {@link Coffee#getAvailable()}.
     * Incorpora los atributos especificos del cafe (origin, process, roastLevel,
     * descriptors) y convierte las variantes a {@link CoffeeVariantDto} mediante
     * {@link CoffeeVariantDto#fromEntity} cuando existan formatos.
     * @param coffee {@link Coffee} entidad persistente con datos de producto.
     * @return {@link CoffeeDto} listo para la vista publica, incluyendo la lista `formats`.
     */
    public static CoffeeDto fromEntity(Coffee coffee) {
        
        CoffeeDto dto = new CoffeeDto();
        
        // de superclase
        dto.setId(coffee.getId());
        dto.setName(coffee.getName());
        dto.setDescription(coffee.getDescription());
        dto.setAdditionalDescription(coffee.getAdditionalDescription());
        dto.setPrice(coffee.getPrice());
        dto.setCategory(coffee.getCategory());
        dto.setSubcategory(coffee.getSubcategory());
        dto.setProductImages(coffee.getProductImages());
        dto.setAvailable(coffee.getAvailable());    // calculado por coffee

        // campos propios
        dto.setOrigin(coffee.getOrigin());
        dto.setProcess(coffee.getProcess());
        dto.setRoastLevel(coffee.getRoastLevel());
        dto.setDescriptors(coffee.getDescriptors());

        // conversion de lista de formatos
        if (coffee.getFormats() != null) {
            List<CoffeeVariantDto> variants = coffee.getFormats().stream()
                // utiliza el metodo
                .map(CoffeeVariantDto::fromEntity)
                .toList();
            dto.setFormats(variants);
        }
        return dto;

    }
}