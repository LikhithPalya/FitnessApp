package com.project.fitness.controller;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@RestController
@RequestMapping("api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(activityService.trackActivity(request, email));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(
            @RequestHeader(value = "X-User-ID") String userId //as we get userId from requestheaders
    ) { //return the activities of the specific user based on user id as a header in url in the get request
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }
}
