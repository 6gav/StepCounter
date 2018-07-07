package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.fitness.data.Goal;

public class GoalsActivity extends AppCompatActivity {

    //Components
    EditText goalsInput;
    Button applyGoalsButton;
    TextView currentGoal;
    RadioButton stepGoalRadioButton,calorieGoalRadioButton;

    //Variables
    int StepsAtGoalCreate,CaloriesAtGoalCreate,GoalAtCreate,GoalType = 0;


    //Preferences
    SharedPreferences GoalTracker, StepTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        //Components
        goalsInput = findViewById(R.id.goalsEditText);
        applyGoalsButton = findViewById(R.id.applyGoalsButton);
        currentGoal = findViewById(R.id.currentGoalTextView);
        stepGoalRadioButton = findViewById(R.id.stepGoalRadioButton);
        calorieGoalRadioButton = findViewById(R.id.calorieGoalRadioButton);

        //Preferences
        GoalTracker = getSharedPreferences("com.stepcountercounter.GoalTracker", Context.MODE_PRIVATE);
        StepTracker = getSharedPreferences("com.stepcountercounter.stepdata", Context.MODE_PRIVATE);



        //Variables
        StepsAtGoalCreate = StepTracker.getInt("StepCount", 0);
        CaloriesAtGoalCreate = Math.round(StepTracker.getFloat("CalorieCount", 0));


        LoadGoal();
    }


    public void RBClick(View v){
    if(stepGoalRadioButton.isChecked())
        GoalType = 0;
    else if(calorieGoalRadioButton.isChecked())
        GoalType = 1;
    }

    public int LoadType(String T){
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

    public void SaveGoal(View v){

        String temp = goalsInput.getText().toString();
        String type = "Steps";
        if(GoalType == 1)type = "Calories";
        GoalAtCreate = LoadType(type);
        if(!temp.equals("")){
            String Goal = type +","+ temp + "," + String.valueOf(GoalAtCreate) + ",";
            GoalTracker.edit().putString("CurrentGoal", Goal).apply();
        }
        LoadGoal();

    }

    public void LoadGoal(){
        String temp = GoalTracker.getString("CurrentGoal", "");
        if(!temp.equals("")){
            String[] Goal = temp.split(",");
            int StepGoal = Integer.valueOf(Goal[1]);
            int OrigSteps = Integer.valueOf(Goal[2]);
            //GoalType: 0 Steps, 1 Calories
            GoalAtCreate = LoadType(Goal[0]);
            int Difference = GoalAtCreate - OrigSteps;


            if(Difference >= StepGoal){
                currentGoal.setText(R.string.GoalCompleted);
                GoalTracker.edit().putString("CurrentGoal", "").apply();
            }
            else {
                String current = Goal[0] + ": " + Difference + "/" + StepGoal;
                currentGoal.setText(current);
            }

        }
        else {
            currentGoal.setText(R.string.NoActiveGoal);
        }
    }


}
