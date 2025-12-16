package com.mitienda.recommendation.controller;

import com.mitienda.product_service.dto.coffee.CoffeeDto;
import com.mitienda.recommendation.dto.CoffeeQuizDto;
import com.mitienda.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /*
     * Obtiene lista con los candidatos Dto para la recomendacion, según datos quizDto.
     */
    @PostMapping("/match")
    public ResponseEntity<List<CoffeeDto>> getMatch(@RequestBody CoffeeQuizDto quizDto) {
        
        var recommendations = recommendationService.getRecommendations(quizDto);

        List<CoffeeDto> responseDtos = recommendations.stream()
                .map(CoffeeDto::fromEntity)
                .toList();

        return ResponseEntity.ok(responseDtos);
    }

}