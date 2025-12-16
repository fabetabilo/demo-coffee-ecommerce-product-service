package com.mitienda.product_service.dto.pack;

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

    private String packDescription;    
    private List<PackItemDto> items;

    /**
     * Convierte una entidad {@link Pack} a su representacion publica.
     * <p>
     * Mapea los campos heredados del producto (id, name, description,
     * additionalDescription, price, category, subcategory, productImages,
     * available) y los atributos propios del pack (packDescription).
     * Tambien transforma los items embebidos mediante
     * {@link PackItemDto#fromEmbeddable} cuando existen componentes.
     * @param pack {@link Pack} entidad persistente con items cargados.
     * @return {@link PackDto} listo para el canal publico.
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