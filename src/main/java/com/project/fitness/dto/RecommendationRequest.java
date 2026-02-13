package com.project.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    private String userId;
    private String activityId; //to find against which activy and userid its stored againat
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

}
