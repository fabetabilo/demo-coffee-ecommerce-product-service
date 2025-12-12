package com.mitienda.product_service.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accessories")
public class Accessory extends Product {
    
    @Column(nullable = false)
    private String brand;

    @ElementCollection
    @CollectionTable(name = "accessory_features", joinColumns = @JoinColumn(name = "accessory_id"))
    @Column(name = "feature",nullable = false)
    private List<String> features;


}
