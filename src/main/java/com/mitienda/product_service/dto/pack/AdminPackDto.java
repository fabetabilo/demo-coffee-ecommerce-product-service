package com.mitienda.product_service.dto.pack;

import com.mitienda.product_service.model.Pack;
import lombok.Data;
import java.util.List;

@Data
public class AdminPackDto {

    private Long id;
    private String name;
    private String description;
    private String additionalDescription;
    private Double price;
    private String category;
    private String subcategory;
    private List<String> productImages;
    private boolean available;

    // === CAMPO EXCLUSIVO ADMIN ===
    private Integer stock;

    private String packDescription;    
    private List<PackItemDto> items;

    /**
     * Convierte una entidad {@link Pack} a su DTO para administracion.
     * <p>
     * Expone los campos generales del producto (id, name, description,
     * additionalDescription, price, category, subcategory, productImages,
     * available) y agrega informacion operativa como {@code stock} y
     * {@code packDescription}. Los items se transforman con
     * {@link PackItemDto#fromEmbeddable} para preservar cantidades y referencias.
     * @param pack {@link Pack} entidad persistente del catalogo.
     * @return {@link AdminPackDto} con datos completos para panel administrativo.
     */
    public static AdminPackDto fromEntity(Pack pack) {
        AdminPackDto dto = new AdminPackDto();

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