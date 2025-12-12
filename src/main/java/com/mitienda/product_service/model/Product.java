package com.mitienda.product_service.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter // no @Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products") // opcional, recomendado si el nombre de la tabla es plural
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = true, columnDefinition = "TEXT") // TEXT/LONGTEXT en mysql: texto ilimitado
    private String additionalDescription;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subcategory;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url", nullable = false)
    private List<String> productImages; // URLs de imagenes como lista de strings

}
