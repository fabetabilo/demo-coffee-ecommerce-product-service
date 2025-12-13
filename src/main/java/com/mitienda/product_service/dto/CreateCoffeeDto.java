package com.mitienda.product_service.dto;

import com.mitienda.product_service.model.Coffee;
import com.mitienda.product_service.model.CoffeeVariant;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateCoffeeDto {
    // --- campos de superclase de Product ---
    private Long id; 
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category; 
    private String subcategory;
    private List<String> productImages;
    // campos propios
    private String origin;
    private String process;
    private Integer roastLevel;
    private List<String> descriptors;
    private List<CreateCoffeeVariantDto> formats; // lista con variantes de formato

    /**
     * Convierte el DTO en una entidad {@link Coffee} lista para persistir en base de datos.
     * @return {@link Coffee} Entidad coffee.
     */
    public Coffee toEntity() {

        Coffee coffee = new Coffee();
        
        coffee.setId(this.id);
        coffee.setName(this.name);
        coffee.setDescription(this.description);
        coffee.setAdditionalDescription(this.additionalDescription);
        coffee.setPrice(this.price);
        coffee.setCategory(this.category);
        coffee.setSubcategory(this.subcategory);
        coffee.setProductImages(this.productImages);

        coffee.setOrigin(this.origin);
        coffee.setProcess(this.process);
        coffee.setRoastLevel(this.roastLevel);
        coffee.setDescriptors(this.descriptors);

        // mapeo de variantes
        if (this.formats != null) {
            // convierte cada CreateCoffeeVariantDto en una entidad CoffeeVariant
            List<CoffeeVariant> variantEntities = this.formats.stream()
                .map(CreateCoffeeVariantDto::toEntity) 
                .toList();
            // envolvemos en ArrayList para garantizar que sea modificable por JPA si es necesario
            coffee.setFormats(new ArrayList<>(variantEntities));
        }
        return coffee;

    }
}