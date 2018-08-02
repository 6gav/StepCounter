package com.usfit.stepcounter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class GoalChecker{

    SharedPreferences GoalTracker,StepTracker,MoneyTracker;
    String[] Goals,Goal;
    Activity activity;
    int StepsAtGoalCreate, CaloriesAtGoalCreate,GoalAtCreate,existingGoals;
    public GoalChecker(Activity _activity){
        activity = _activity;
        GoalTracker = _activity.getSharedPreferences("com.usfit.stepcounter.GoalTracker", Context.MODE_PRIVATE);
        StepTracker = _activity.getSharedPreferences("com.usfit.stepcounter.stepdata", Context.MODE_PRIVATE);
        MoneyTracker = _activity.getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);

        StepsAtGoalCreate = StepTracker.getInt("StepCount", 0);
        CaloriesAtGoalCreate = Math.round(StepTracker.getFloat("CalorieCount", 0));
        existingGoals = GoalTracker.getInt("GoalCount",0);

    }

    int LoadType(String T){
        int goalAtCreate = StepsAtGoalCreate;

        switch (T){
            case "Steps":
                break;
            case "Calories":
                goalAtCreate = CaloriesAtGoalCreate;
                break;

        }
        return goalAtCreate;
    }

    void Check(Context mContext){
        String temp = GoalTracker.getString("CurrentGoals", "");
        if(!temp.equals("")) {

            Goals = temp.split(";");
            for(int i = 0;i < Goals.length;i++){
                Goals[i] = HandleGoal(Goals[i].split(","),temp,mContext);
            }
        }
    }
    String HandleGoal(String[] _Goal,String temp,Context mContext){

        Goal = _Goal;int StepGoal = Integer.valueOf(Goal[1]);
        int OrigSteps = Integer.valueOf(Goal[2]);
        //GoalType: 0 Steps, 1 Calories
        GoalAtCreate = LoadType(Goal[0]);
        int Difference = GoalAtCreate - OrigSteps;

        if (Difference >= StepGoal) {//goal was completed
            Toast.makeText(mContext,"You Completed your goal of "+StepGoal+" "+Goal[0], Toast.LENGTH_SHORT);
            CompleteGoal(Goal[0],StepGoal);
            return "";
        } else {
            temp = Goal[0] + ": " + Difference + "/" + StepGoal;
            boolean goalExists = false;
            for(int j = 0; j < Goals.length;j++) {
                if(Goals[j]!= null){
                    if(String.valueOf(StepGoal)==Goals[j].split(",")[1]){//goal exists in array
                        goalExists = true;
                    }
                }
            }if(!goalExists) {
                return Goal[0] + "," + StepGoal + "," + OrigSteps + ";";
                //TODO:currentGoal.setText("");
            }
        }
        Toast.makeText(mContext,"NOOO", Toast.LENGTH_SHORT);
        return "";
    }
    public void CompleteGoal(String type, int stepsTaken){
        int moneyEarned = (int)Math.pow(stepsTaken,1.3f),
                OrigMoney = MoneyTracker.getInt("MonValue",0);

        MoneyTracker.edit().putInt("MonValue",moneyEarned+OrigMoney);
        existingGoals--;
        if(existingGoals<0)existingGoals=0;
        GoalTracker.edit().putInt("GoalCount",existingGoals);
    }
}
