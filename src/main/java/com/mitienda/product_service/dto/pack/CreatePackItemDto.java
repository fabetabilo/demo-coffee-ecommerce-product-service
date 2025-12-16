package com.mitienda.product_service.dto.pack;

import com.mitienda.product_service.model.PackItem;
import lombok.Data;

@Data
public class CreatePackItemDto {

    private String name;
    private String label;
    private Integer grams;
    private String process;
    private Long originalCoffeeId; // nullable

    /**
     * Convierte el DTO en un objeto embeddable (incrustable) {@link PackItem} para persistir en la base de datos..
     */
    public PackItem toEmbeddable() {

        PackItem item = new PackItem();
        
        item.setName(this.name);
        item.setLabel(this.label);
        item.setGrams(this.grams);
        item.setProcess(this.process);
        item.setOriginalCoffeeId(this.originalCoffeeId);
        
        return item;
    }
}