package com.example.recommendation.controller;

import com.example.recommendation.dto.RecommendationDto;
import com.example.recommendation.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "recommendation-service is alive";
    }

    @GetMapping
    public List<RecommendationDto> recommend(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return recommendationService.recommend(userId, limit);
    }
}
