package com.mitienda.product_service.dto.pack;

import com.mitienda.product_service.model.PackItem;
import lombok.Data;

@Data
public class PackItemDto {

    private String name;
    private String label;
    private Integer grams;
    private String process;
    private Long originalCoffeeId;

    /**
     * Convierte el objeto {@link PackItem} a DTO.
     */
    public static PackItemDto fromEmbeddable(PackItem item) {
        PackItemDto dto = new PackItemDto();
        
        dto.setName(item.getName());
        dto.setLabel(item.getLabel());
        dto.setGrams(item.getGrams());
        dto.setProcess(item.getProcess());
        dto.setOriginalCoffeeId(item.getOriginalCoffeeId());
        
        return dto;
    }
}