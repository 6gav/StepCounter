package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GoalsActivity extends AppCompatActivity {

    //Components
    EditText goalsInput;
    Button applyGoalsButton;
    TextView currentGoal;

    //Variables
    int StepsAtGoalCreate;


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


        //Preferences
        GoalTracker = getSharedPreferences("com.stepcountercounter.GoalTracker", Context.MODE_PRIVATE);
        StepTracker = getSharedPreferences("com.stepcountercounter.stepdata", Context.MODE_PRIVATE);



        //Variables
        StepsAtGoalCreate = StepTracker.getInt("StepCount", 0);



        LoadGoal();
    }





    public void SaveGoal(View v){

        String temp = goalsInput.getText().toString();
        if(!temp.equals("")){
            String Goal = "Steps," + temp + "," + String.valueOf(StepsAtGoalCreate) + ",";
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
            int Difference = StepsAtGoalCreate - OrigSteps;


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
