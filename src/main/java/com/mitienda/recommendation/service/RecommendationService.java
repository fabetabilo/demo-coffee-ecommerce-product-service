package com.mitienda.recommendation.service;

import com.mitienda.product_service.model.Coffee;
import com.mitienda.product_service.model.enums.FlavorProfile; // Asegúrate de importar esto
import com.mitienda.product_service.model.enums.RoastIntensity;
import com.mitienda.product_service.service.CoffeeService;
import com.mitienda.recommendation.dto.CoffeeQuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final CoffeeService coffeeService;

    /**
     * 
     * @param quiz
     * @return
     */
    public List<Coffee> getRecommendations(CoffeeQuizDto quiz) {
        
        List<Coffee> allCoffees = coffeeService.findAllCoffees();

        return allCoffees.stream()
                .filter(Coffee::getAvailable)   // se asegura de filtrar a aquellos que estan disponibles segun su stock
                // 1. Filtro por metodo
                .filter(coffee -> coffee.getSuitableMethods() != null && coffee.getSuitableMethods().contains(quiz.getBrewingMethod()))
                // 2. Ordena por puntaje los candidatos coffee
                .sorted(Comparator.comparingInt((Coffee coffee) -> calculateScore(coffee, quiz)).reversed())
                // 3. Se puede limitar el resultado del quiz
                .limit(3)
                .collect(Collectors.toList());
    }


    /**
     * Se encarga de definir un puntaje al candidato según datos de quiz.
     * @param coffee {@link Coffee} cafe de la base de datos que procesa.
     * @param quiz {@link CoffeeQuizDto} datos para el cálculo de puntaje
     * @return Int score, puntaje obtenido del candidato coffee
     */
    private int calculateScore(Coffee coffee, CoffeeQuizDto quiz) {
        
        int score = 0;
        /* 1. Sobre flavor profile (+10 Puntos)
        Si elige SURPRISE le gusta todo -> +10 automático
        o si elige un sabor específico, debe coincidir. */
        if (quiz.getFlavorPreference() == FlavorProfile.SURPRISE || 
            quiz.getFlavorPreference() == coffee.getFlavorProfile()) {
            score += 10;
        }

        /* 2. Sobre milk (+5 Puntos o resta)
        Se asume la preferencia de tuestes medios y altos para bebidas con leche y
        tuestes ligeros se descartan. En caso de ser cafe negro, se decide por flavorProfile/roastIntensity.
        Opciones: "YES", "SOMETIMES", "NO" (Solo o café negro) */
        String milk = quiz.getMilkPreference().toUpperCase();
        
        if ("YES".equals(milk) || "SOMETIMES".equals(milk)) {
           
            if (coffee.getRoastIntensity() == RoastIntensity.MEDIUM || 
                coffee.getRoastIntensity() == RoastIntensity.DARK) {
                score += 5;
            // if "NO"
            } else if (coffee.getRoastIntensity() == RoastIntensity.LIGHT) {
                score -= 5;
            }
        } 

        /* 3. Sobre preferencia de nivel de tueste o intensidad (+5 Puntos)
        Opciones: "LIGHT", "MEDIUM", "DARK" en el string. */
        if (quiz.getRoastPreference().equalsIgnoreCase(coffee.getRoastIntensity().name())) {
            score += 5;
        }

        return score;

    }
}