package com.mitienda.product_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** CoffeeVariant
 * - Clase auxiliar que solo existe "dentro" de Coffee. Esta clase permite a un id Coffee
 * tener diferentes formatos; cada uno con su propio precio, propio stock, y su propio id
 * para gestionar la venta.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coffee_variants")
public class CoffeeVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id unico que representa el producto real

    @Column(nullable = false)
    private String label; // "250g, 1kg"

    @Column(nullable = false)
    private Integer grams; // formato en gramos de la variante

    @Column(nullable = false)
    private Double price;   // !!! TEMPORAL: 'BigDecimal' para precios

    @Column(nullable = false)
    private Boolean available; // true/false, controla la disponibilidad

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", nullable = false)
    @JsonIgnore // evita bucles del superclass
    private Coffee coffee;

}
