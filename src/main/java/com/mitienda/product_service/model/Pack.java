package com.mitienda.product_service.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "packs")
public class Pack extends Product {

    @Column(nullable = false)
    private Integer stock; // stock independiente, no descuenta del stock de los cafess individuales existentes

    @Column(nullable = true, length = 1000)
    private String packDescription;

    @ElementCollection(fetch = FetchType.EAGER) // EAGER para que cargue los items al pedir el pack
    @CollectionTable(name = "pack_items", joinColumns = @JoinColumn(name = "pack_id"))
    private List<PackItem> items;

    /**
     * Obtiene disponibilidad de pack.
     * @return true para cuando stock > 0, false si stock = 0
     */
    public boolean getAvailable() {
        return this.stock != null && this.stock > 0;
    }
}