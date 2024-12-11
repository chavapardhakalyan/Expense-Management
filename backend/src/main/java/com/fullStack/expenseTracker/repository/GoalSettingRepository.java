package com.fullStack.expenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fullStack.expenseTracker.models.GoalSetting;

import java.util.Date;
import java.util.List;

@Repository
public interface GoalSettingRepository extends JpaRepository<GoalSetting, Long> {

    @Query(value = "UPDATE goal_setting SET amount_saved = :amountSaved, completed = :completed " +
            "WHERE id = :id", nativeQuery = true)
    void updateAmountSaved(@Param("id") Long id,
            @Param("amountSaved") double amountSaved,
            @Param("completed") boolean completed);

    @Query(value = "UPDATE goal_setting SET name = :name, price = :price, target_date = :targetDate " +
                   "WHERE id = :id", nativeQuery = true)
    void editGoal(@Param("id") Long id,
            @Param("name") String name,
            @Param("price") double price,
            @Param("targetDate") Date targetDate);

    @Query(value = "DELETE FROM goal_setting WHERE id = :id", nativeQuery = true)
    void deleteGoal(@Param("id") Long id);

    @Query(value = "SELECT * FROM goal_setting", nativeQuery = true)
    List<GoalSetting> getAllGoals();

    @Query(value = "SELECT * FROM goal_setting WHERE completed = true", nativeQuery = true)
    List<GoalSetting> getCompletedGoals();

    @Query(value = "SELECT * FROM goal_setting WHERE price > amount_saved", nativeQuery = true)
    List<GoalSetting> getGoalsWithRemainingAmount();

    @Query(value = "SELECT * FROM goal_setting WHERE target_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<GoalSetting> getGoalsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "SELECT * FROM goal_setting ORDER BY price ASC", nativeQuery = true)
    List<GoalSetting> getGoalsByPriceAsc();

    @Query(value = "SELECT * FROM goal_setting ORDER BY target_date ASC", nativeQuery = true)
    List<GoalSetting> getGoalsByTargetDateAsc();

    @Query(value = "SELECT * FROM goal_setting WHERE name LIKE %:name%", nativeQuery = true)
    List<GoalSetting> getGoalsByName(@Param("name") String name);

    @Query(value = "SELECT * FROM goal_setting WHERE price = (SELECT MAX(price) FROM goal_setting)", nativeQuery = true)
    GoalSetting getGoalWithMaxPrice();

    @Query(value = "SELECT * FROM goal_setting WHERE price = (SELECT MIN(price) FROM goal_setting)", nativeQuery = true)
    GoalSetting getGoalWithMinPrice();

    @Query(value = "SELECT * FROM goal_setting WHERE user_id = :userId", nativeQuery = true)
    List<GoalSetting> getGoalsByUserId(@Param("userId") Long userId);

    @Query(value = "UPDATE goal_setting SET completed = :completed WHERE id = :id", nativeQuery = true)
    void updateGoalCompletionStatus(@Param("id") Long id, @Param("completed") boolean completed);

    @Query(value = "SELECT * FROM goal_setting ORDER BY amount_saved DESC", nativeQuery = true)
    List<GoalSetting> getGoalsByAmountSavedDesc();

    @Query(value = "SELECT * FROM goal_setting WHERE (price - amount_saved) < :threshold", nativeQuery = true)
    List<GoalSetting> getGoalsNearCompletion(@Param("threshold") double threshold);

}
