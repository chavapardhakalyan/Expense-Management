package com.fullStack.expenseTracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.models.GoalSetting;
import com.fullStack.expenseTracker.services.GoalSettingService;

@RestController
@RequestMapping("/api/goals")
public class GoalSettingController {

    @Autowired
    private GoalSettingService goalSettingService;

    @PostMapping("/add")
    // @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponseDto<?>> addGoal(@RequestBody GoalSetting goalSetting) throws Exception {
        return goalSettingService.addGoal(goalSetting);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GoalSetting> updateGoal(@PathVariable Long id, @RequestParam Double amount) {
        GoalSetting updatedGoal = goalSettingService.updateGoal(id, amount);
        return ResponseEntity.ok(updatedGoal);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalSettingService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<GoalSetting> editGoal(@PathVariable Long id, @RequestBody GoalSetting goalSetting) {
        GoalSetting editedGoal = goalSettingService.editGoal(id, goalSetting);
        return ResponseEntity.ok(editedGoal);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GoalSetting>> getAllGoals() {
        List<GoalSetting> goals = goalSettingService.getAllGoals();
        return ResponseEntity.ok(goals);
    }
}