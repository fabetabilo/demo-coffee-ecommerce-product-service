package com.mitienda.recommendation.dto;

import com.mitienda.product_service.model.enums.SuitableMethod;
import com.mitienda.product_service.model.enums.FlavorProfile;
import lombok.Data;

@Data
public class CoffeeQuizDto {

    private SuitableMethod brewingMethod;   // valores exactos: "ESPRESSO", "PRENSA_FRANCESA", etc.

    private String milkPreference; // valores exactos: "YES", "SOMETIMES", "NO"

    private FlavorProfile flavorPreference;  // valores exactos: "CHOCOLATE", "FRUITY", "FLORAL_DELICATE", "COFFEE"

    private String roastPreference; // valores exactos: "LIGHT", "MEDIUM", "DARK"
}