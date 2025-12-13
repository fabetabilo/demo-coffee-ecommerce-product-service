package com.mitienda.product_service.dto;

import com.mitienda.product_service.model.Accessory;

import lombok.Data;
import java.util.List;

@Data
public class CreateAccessoryDto {
    // --- campos superclase de Product ---
    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    // campos propios
    private String brand;
    private List<String> features; 
    private Integer stock; // stock directo del producto

    /**
     * Convierte el DTO en una entidad {@link Accessory} lista para persistir en base de datos.
     * @return {@link Accessory} Entidad accessory.
     */
    public Accessory toEntity() {

        Accessory accessory = new Accessory();

        accessory.setId(this.id);
        accessory.setName(this.name);
        accessory.setDescription(this.description);
        accessory.setAdditionalDescription(this.additionalDescription);
        accessory.setPrice(this.price);
        accessory.setCategory(this.category);
        accessory.setSubcategory(this.subcategory);
        accessory.setProductImages(this.productImages);

        accessory.setBrand(this.brand);
        accessory.setFeatures(this.features);
        accessory.setStock(this.stock);

        return accessory;

    }
}