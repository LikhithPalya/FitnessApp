package com.project.fitness.controller;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.model.Recommendation;
import com.project.fitness.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(
            @RequestBody RecommendationRequest request
    ){
        Recommendation recommendation = recommendationService.generaterecommendations(request);
        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(
            @PathVariable("userid") String userId //accepitng a dynamic variable via the path
    ){
        List<Recommendation> recommendationList = recommendationService.getUserRecommendation(userId); //a single user can have multiple recommendations
        return ResponseEntity.ok(recommendationList);
    }



}
