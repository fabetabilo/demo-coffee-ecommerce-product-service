package com.mitienda.product_service.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/** Coffee
 * - Clase que representa un concepto de cafe en la tienda.
 * Contiene informacion del origen, proceso o beneficio, nivel de tueste (representacion visual),
 * lista con descriptores del cafe, y lista de las variantes del cafe: 100g, 250g, 1kg.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coffees")
public class Coffee extends Product {

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String process;

    @Column(nullable = false)
    private Integer roastLevel;

    @ElementCollection
    @CollectionTable(name = "coffee_descriptors", joinColumns = @JoinColumn(name = "coffee_id"))
    @Column(name = "descriptor",nullable = false)
    private List<String> descriptors;

    @OneToMany(mappedBy = "coffee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeVariant> formats;



    /**
     * Determina la disponibilidad global de cafe en el catalogo.
     * <p>
     * Verifica la lista de formatos ({@link CoffeeVariant}) asociados.
     * El producto se considera disponible (true) si al menos uno de sus
     * formatos tiene stock disponible.
     * @return true si existe al menos un formato con stock > 0.
     * false si no hay formatos definidos o estan todos agotados.
     */
    public boolean getAvailable() {
        if (this.formats == null || this.formats.isEmpty()) {
            return false;
        }
        // recorre lista y revisa que al menos uno tiene stock > 0
        return formats.stream().anyMatch(variant -> variant.getStock() > 0);
    }

}
