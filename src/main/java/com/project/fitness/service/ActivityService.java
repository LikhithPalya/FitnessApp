package com.project.fitness.service;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest request, String email) { //logic used to track actvity qand stored at db level

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("invalid user: " + email);
        }

        Activity activity = Activity.builder()
                .user(user) // since activity request doesnt have user obejct we wil fetch from db using userId
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())

                .build();

        activityRepository.save(activity); // save activity object with all atteributes

        //we dont have activity resopnse objec so we are manually constructing using the saved activity

        Activity savedActivity = activityRepository.save(activity);

        return mapToReponse(savedActivity);
    }

    private ActivityResponse mapToReponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setId(activity.getId());
        response.setUserId(activity.getUser().getId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAddtionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }


    public @Nullable List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList = activityRepository.findByUserId(userId); //findAllByUserId is not a default method, so we make a custom method
        //S1 - activity transform to ativityrespomse
        // S2 collcet in list and return

        return activityList.stream()
                .map(this::mapToReponse)
                .collect(Collectors.toList());
    }
}
