package com.fullStack.expenseTracker.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.models.GoalSetting;

@Service
public interface GoalSettingService {

    ResponseEntity<ApiResponseDto<?>> addGoal(GoalSetting goalSetting) throws Exception;

    GoalSetting updateGoal(Long id, Double amount);

    void deleteGoal(Long id);

    GoalSetting editGoal(Long id, GoalSetting goalSetting);

    List<GoalSetting> getAllGoals();
}