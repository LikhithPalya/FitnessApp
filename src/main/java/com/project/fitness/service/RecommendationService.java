package com.project.fitness.service;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final RecommendationRepository recommendationRepository;

    public Recommendation generaterecommendations(RecommendationRequest request) {
        // we have to retreive object details using userid and activityid as they refer the respective tables
        User user = userRepository.findById(request.getUserId())
        .orElseThrow(()-> new RuntimeException("user not found for userId: " +request.getUserId()));

        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(()-> new RuntimeException("activity not found for activityId: " +request.getActivityId()));

        //constructing recommendation object using builder
        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .build();

        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId); // we dont want recommendation i.e id iinstead we want user mapped with recommendation
    }

    public List<Recommendation> getActivityRecommendation(String activityid) {
        return recommendationRepository.findByActivityId(activityid);
    }
}
