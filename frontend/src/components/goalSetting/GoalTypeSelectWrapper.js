import React from 'react';

function GoalTypeSelectWrapper({ goalTypes, setGoalType, activeGoalType }) {
  return (
    <div className="goal-type-select-wrapper">
      {goalTypes.map((type) => (
        <button 
          key={type.id} 
          onClick={() => setGoalType(type.id)} 
          className={activeGoalType === type.id ? 'active' : ''}
        >
          {type.name}
        </button>
      ))}
    </div>
  );
}

export default GoalTypeSelectWrapper;
