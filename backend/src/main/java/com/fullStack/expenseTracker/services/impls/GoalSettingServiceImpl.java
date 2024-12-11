package com.fullStack.expenseTracker.services.impls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.enums.ApiResponseStatus;
import com.fullStack.expenseTracker.exceptions.TransactionServiceLogicException;
import com.fullStack.expenseTracker.models.GoalSetting;
import com.fullStack.expenseTracker.repository.GoalSettingRepository;
import com.fullStack.expenseTracker.services.GoalSettingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GoalSettingServiceImpl implements GoalSettingService {

    @Autowired
    private GoalSettingRepository goalSettingRepo;

    @Override
    public ResponseEntity<ApiResponseDto<?>> addGoal(GoalSetting goalSetting) throws Exception {
        try {
            goalSettingRepo.save(goalSetting);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponseDto<>(
                            ApiResponseStatus.SUCCESS,
                            HttpStatus.CREATED,
                            "Goal has been successfully recorded!"
                    )
            );
        } catch (Exception e) {
            log.error("Error happen when adding new transaction: " + e.getMessage());
            throw new Exception("Failed to record your new transaction, Try again later!");
        }
    }

    @Override
    public GoalSetting updateGoal(Long id, Double amount) {
        Optional<GoalSetting> existingGoalOpt = goalSettingRepo.findById(id);
        if (existingGoalOpt.isPresent()) {
            GoalSetting existingGoal = existingGoalOpt.get();
            existingGoal.setAmountSaved(existingGoal.getAmountSaved() + amount);
            if (existingGoal.getAmountSaved() >= existingGoal.getPrice()) {
                existingGoal.setCompleted(true);
            }
            return goalSettingRepo.save(existingGoal);
        }
        throw new RuntimeException("Goal not found");
    }

    @Override
    public void deleteGoal(Long id) {
        goalSettingRepo.deleteById(id);
    }

    @Override
    public GoalSetting editGoal(Long id, GoalSetting goalSetting) {
        Optional<GoalSetting> existingGoalOpt = goalSettingRepo.findById(id);
        if (existingGoalOpt.isPresent()) {
            GoalSetting existingGoal = existingGoalOpt.get();
            existingGoal.setName(goalSetting.getName());
            existingGoal.setPrice(goalSetting.getPrice());
            existingGoal.setTargetDate(goalSetting.getTargetDate());
            return goalSettingRepo.save(existingGoal);
        }
        throw new RuntimeException("Goal not found");
    }

    @Override
    public List<GoalSetting> getAllGoals() {
        return goalSettingRepo.findAll();
    }
}