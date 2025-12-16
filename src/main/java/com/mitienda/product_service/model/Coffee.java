package com.mitienda.product_service.model;

import java.util.List;

import com.mitienda.product_service.model.enums.SuitableMethod;
import com.mitienda.product_service.model.enums.FlavorProfile;
import com.mitienda.product_service.model.enums.RoastIntensity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "roast_intensity")
    private RoastIntensity roastIntensity; // se calcula con roastLevel, LIGHT, MEDIUM o DARK

    @Enumerated(EnumType.STRING)
    @Column(name = "flavor_profile")
    private FlavorProfile flavorProfile; // ingresado manualmente

    @ElementCollection(targetClass = SuitableMethod.class)
    @CollectionTable(name = "coffee_suitable_methods", joinColumns = @JoinColumn(name = "coffee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private List<SuitableMethod> suitableMethods; // ingresado manualmente


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

    /**
     * Calcula el nivel de tueste (LIGHT, MEDIUM o DARK) según roastLevel desde 1 a 7.
     */
    @PrePersist
    @PreUpdate
    public void calculateRoastIntensity() {
        if (this.roastLevel != null) {
            if (this.roastLevel <= 2) {
                this.roastIntensity = RoastIntensity.LIGHT;
            } else if (this.roastLevel <= 5) {
                this.roastIntensity = RoastIntensity.MEDIUM;
            } else {
                this.roastIntensity = RoastIntensity.DARK;
            }
        }
    }

}
