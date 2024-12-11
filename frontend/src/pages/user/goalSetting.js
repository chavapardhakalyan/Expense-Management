import React, { useState, useEffect } from 'react';
import Container from '../../components/utils/Container'; 
import Header from '../../components/utils/header'; 
import '../../assets/styles/goalSetting.css'; 
import toast, { Toaster } from 'react-hot-toast';

const GoalSetting = () => {
  const [goalName, setGoalName] = useState('');
  const [goalPrice, setGoalPrice] = useState('');
  const [goalDate, setGoalDate] = useState('');
  const [goalList, setGoalList] = useState([]);
  const [editingGoal, setEditingGoal] = useState(null);
  const [amountToAdd, setAmountToAdd] = useState({}); 
  const [showConfetti, setShowConfetti] = useState(false); 

  useEffect(() => {
    const storedGoals = JSON.parse(localStorage.getItem('goals')) || [];
    setGoalList(storedGoals);
  }, []);

  useEffect(() => {
    localStorage.setItem('goals', JSON.stringify(goalList));
  }, [goalList]);

  const handleAddGoal = () => {
    const targetDate = new Date(goalDate);

    if (goalName.trim() === '' || goalPrice.trim() === '' || isNaN(goalPrice) || Number(goalPrice) <= 0 || !goalDate || isNaN(targetDate.getTime())) {
      toast.error('Please enter a valid goal name, price, and target date.', { position: 'top-center' });
      return;
    }

    const newGoal = {
      id: Date.now(),
      name: goalName,
      price: parseFloat(goalPrice),
      amountSaved: 0,
      completed: false,
      targetDate: targetDate
    };

    setGoalList([...goalList, newGoal]);
    setGoalName('');
    setGoalPrice('');
    setGoalDate('');
    toast.success('Goal added successfully!', { position: 'top-center' });
  };

  const handleUpdateAmountSaved = (id, amount) => {
    const updatedGoals = goalList.map(goal => {
      if (goal.id === id) {
        const newAmountSaved = goal.amountSaved + amount;
        const updatedGoal = {
          ...goal,
          amountSaved: newAmountSaved >= goal.price ? goal.price : newAmountSaved,
          completed: newAmountSaved >= goal.price
        };

        if (updatedGoal.completed) {
          setShowConfetti(true);
          setTimeout(() => setShowConfetti(false), 3000);
        }

        return updatedGoal;
      }
      return goal;
    });
    setGoalList(updatedGoals);
    toast.success('Amount updated successfully!', { position: 'top-center' });
  };

  const handleEditGoal = (goal) => {
    setEditingGoal(goal);
    setGoalName(goal.name);
    setGoalPrice(goal.price.toString());
    const targetDate = goal.targetDate instanceof Date ? goal.targetDate : new Date(goal.targetDate);
  
  if (!isNaN(targetDate.getTime())) {
    setGoalDate(targetDate.toISOString().split('T')[0]);
  } else {
    toast.error('Invalid target date for this goal.', { position: 'top-center' });
  }
};

  const handleSaveEditedGoal = () => {
    const targetDate = new Date(goalDate);

    if (goalName.trim() === '' || goalPrice.trim() === '' || isNaN(goalPrice) || Number(goalPrice) <= 0 || !goalDate || isNaN(targetDate.getTime())) {
      toast.error('Please enter valid goal details.', { position: 'top-center' });
      return;
    }

    const updatedGoals = goalList.map(goal => {
      if (goal.id === editingGoal.id) {
        return { ...goal, name: goalName, price: parseFloat(goalPrice), targetDate: targetDate };
      }
      return goal;
    });

    setGoalList(updatedGoals);
    setEditingGoal(null);
    setGoalName('');
    setGoalPrice('');
    setGoalDate('');
    toast.success('Goal updated successfully!', { position: 'top-center' });
  };

  const handleDeleteGoal = (id) => {
    const updatedGoals = goalList.filter(goal => goal.id !== id);
    setGoalList(updatedGoals);
    toast.error('Goal deleted!', { position: 'top-center' });
  };

  const handleAmountInputChange = (e, id) => {
    setAmountToAdd({ ...amountToAdd, [id]: e.target.value });
  };

  const handleAddAmountToGoal = (goal) => {
    const amount = parseFloat(amountToAdd[goal.id]);

    if (isNaN(amount) || amount <= 0) {
      toast.error('Please enter a valid amount.', { position: 'top-center' });
      return;
    }

    handleUpdateAmountSaved(goal.id, amount);

    setAmountToAdd({ ...amountToAdd, [goal.id]: '' });
  };

  const calculateRemainingAmount = (goal) => {
    const remainingAmount = goal.price - goal.amountSaved;
    return remainingAmount > 0 ? remainingAmount : 0;
  };

  const calculateMonthlySavings = (goal) => {
    const remainingAmount = calculateRemainingAmount(goal);
    const currentDate = new Date();
    const timeDiff = new Date(goal.targetDate) - currentDate;
    const remainingMonths = Math.ceil(timeDiff / (1000 * 3600 * 24 * 30));

    return remainingMonths > 0 ? (remainingAmount / remainingMonths).toFixed(2) : 0;
  };

  return (
    <Container activeNavId={12}>
      <Header title="Goal Setting" />
      <div className="goal-setting-container">
        <h1 className="page-title">Goal Setting</h1>

        <div className="goal-input">
          <input type="text" placeholder="Enter goal name" value={goalName} onChange={(e) => setGoalName(e.target.value)} className="goal-input-field" />
          <input type="number" placeholder="Enter goal price" value={goalPrice} onChange={(e) => setGoalPrice(e.target.value)} className="goal-input-field" />
          <input type="date" value={goalDate} onChange={(e) => setGoalDate(e.target.value)} className="goal-input-field" />
          <button onClick={editingGoal ? handleSaveEditedGoal : handleAddGoal} className="add-goal-btn" style={{backgroundColor: "#4389df", borderRadius: "25px"}}>
            {editingGoal ? 'Save Changes' : 'Add Goal'}
          </button>
        </div>

        <h2 className="goal-list-title">Your Goals</h2>
        <ul className="goal-list">
          {goalList.map(goal => (
            <li key={goal.id} className="goal-item">
              <div className="goal-details">
                <h3>{goal.name}</h3>
                <p><strong>Price:</strong> {goal.price.toFixed(2)}</p>
                <p><strong>Amount Saved:</strong> {goal.amountSaved.toFixed(2)}</p>
                <p><strong>Remaining Amount:</strong> {calculateRemainingAmount(goal).toFixed(2)}</p>
                <p><strong>Monthly Savings Needed:</strong> {calculateMonthlySavings(goal)}</p>
                <p><strong>Target Date:</strong> {new Date(goal.targetDate).toLocaleDateString('en-GB')}</p>
              </div>
              <div className="goal-actions">
                <input type="number" placeholder="Enter Amount to Add" min="0" value={amountToAdd[goal.id] || ''} onChange={(e) => handleAmountInputChange(e, goal.id)} className="amount-input" />
                <button onClick={() => handleAddAmountToGoal(goal)} className="add-amount-btn" style={{backgroundColor : "blue", borderRadius: "25px"}}>Add Amount</button>
                <button onClick={() => handleEditGoal(goal)} className="edit-goal-btn" style={{backgroundColor : "orange", borderRadius: "25px"}}>Edit</button>
                <button onClick={() => handleDeleteGoal(goal.id)} className="delete-goal-btn" style={{backgroundColor : "red", borderRadius: "25px"}}>Delete</button>
              </div>
            </li>
          ))}
        </ul>
      </div>

      {showConfetti && (
        <div className="confetti-container visible">
          {Array.from({ length: 100 }).map((_, index) => (
            <div key={index} className="confetti" style={{ left: `${Math.random() * 100}vw`, animationDuration: `${Math.random() * 2 + 3}s` }} />
          ))}
        </div>
      )}
      <Toaster position="top-center" />
    </Container>
  );
};

export default GoalSetting;
