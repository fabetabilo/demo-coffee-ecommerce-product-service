package com.mitienda.product_service.dto;

import com.mitienda.product_service.model.Pack;
import com.mitienda.product_service.model.PackItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreatePackDto {
    // --- campos de superclase Product ---
    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;

    // --- campos propios de Pack ---
    private Integer stock;
    private String packDescription;
    private List<CreatePackItemDto> items; // Lista de items para crear

    /**
     * Convierte el DTO en una entidad {@link Pack} para persistir en la base de datos.
     * @return {@link Pack} pack
     */
    public Pack toEntity() {

        Pack pack = new Pack();

        pack.setId(this.id);
        pack.setName(this.name);
        pack.setDescription(this.description);
        pack.setAdditionalDescription(this.additionalDescription);
        pack.setPrice(this.price);
        pack.setCategory(this.category);
        pack.setSubcategory(this.subcategory);
        pack.setProductImages(this.productImages);
        
        pack.setStock(this.stock);
        pack.setPackDescription(this.packDescription);
        // setea los items que corresponden al pack
        if (this.items != null) {
            List<PackItem> itemEmbeddables = this.items.stream()
                .map(CreatePackItemDto::toEmbeddable)
                .toList();
            pack.setItems(new ArrayList<>(itemEmbeddables));
        }

        return pack;
    }
}