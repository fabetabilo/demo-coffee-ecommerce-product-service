package com.mitienda.product_service.dto;

import com.mitienda.product_service.model.Pack;
import lombok.Data;
import java.util.List;

@Data
public class PackDto {

    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    private boolean available; // calculado

    private Integer stock;
    private String packDescription;    
    private List<PackItemDto> items;

    /**
     * Convierte una entidad {@link Pack} a su DTO.
     */
    public static PackDto fromEntity(Pack pack) {

        PackDto dto = new PackDto();

        dto.setId(pack.getId());
        dto.setName(pack.getName());
        dto.setDescription(pack.getDescription());
        dto.setAdditionalDescription(pack.getAdditionalDescription());
        dto.setPrice(pack.getPrice());
        dto.setCategory(pack.getCategory());
        dto.setSubcategory(pack.getSubcategory());
        dto.setProductImages(pack.getProductImages());
        dto.setAvailable(pack.getAvailable());

        dto.setStock(pack.getStock());
        dto.setPackDescription(pack.getPackDescription());

        if (pack.getItems() != null) {
            List<PackItemDto> itemDtos = pack.getItems().stream()
                .map(PackItemDto::fromEmbeddable)
                .toList();
            dto.setItems(itemDtos);
        }

        return dto;
    }
}