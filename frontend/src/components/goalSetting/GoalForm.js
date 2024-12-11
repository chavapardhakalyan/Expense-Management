import React, { useState } from 'react';

function GoalForm({ categories, onSubmit, isSaving }) {
  const [goalData, setGoalData] = useState({
    goalName: '',
    goalAmount: '',
    savedSoFar: '',
    targetDate: '',
    monthlyContribution: ''
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(goalData);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input 
        type="text" 
        placeholder="Goal Name" 
        value={goalData.goalName} 
        onChange={(e) => setGoalData({ ...goalData, goalName: e.target.value })} 
        required 
      />
      <input 
        type="number" 
        placeholder="Goal Amount" 
        value={goalData.goalAmount} 
        onChange={(e) => setGoalData({ ...goalData, goalAmount: e.target.value })} 
        required 
      />
      <input 
        type="number" 
        placeholder="Saved So Far" 
        value={goalData.savedSoFar} 
        onChange={(e) => setGoalData({ ...goalData, savedSoFar: e.target.value })} 
        required 
      />
      <input 
        type="date" 
        value={goalData.targetDate} 
        onChange={(e) => setGoalData({ ...goalData, targetDate: e.target.value })} 
        required 
      />
      <button type="submit" disabled={isSaving}>
        {isSaving ? 'Saving...' : 'Save Goal'}
      </button>
    </form>
  );
}

export default GoalForm;
