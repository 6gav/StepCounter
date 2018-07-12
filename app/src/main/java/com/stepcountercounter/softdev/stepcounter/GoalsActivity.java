package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.fitness.data.Goal;

public class GoalsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //Components
    EditText goalsInput;
    Button applyGoalsButton;
    TextView currentGoal;
    RadioButton stepGoalRadioButton,calorieGoalRadioButton;
    ListView lvCurrentGoals;

    //Variables
    int StepsAtGoalCreate,CaloriesAtGoalCreate,GoalAtCreate,GoalType = 0,GoalIndex = -1,clicks;
    String[] Goals;

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
        lvCurrentGoals = findViewById(R.id.lvCurrentGoals);



        //Preferences
        GoalTracker = getSharedPreferences("com.stepcountercounter.GoalTracker", Context.MODE_PRIVATE);
        StepTracker = getSharedPreferences("com.stepcountercounter.stepdata", Context.MODE_PRIVATE);



        //Variables
        StepsAtGoalCreate = StepTracker.getInt("StepCount", 0);
        CaloriesAtGoalCreate = Math.round(StepTracker.getFloat("CalorieCount", 0));
        Goals = new String[]{"","",""};

        LoadGoal();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(GoalIndex == position) {
            clicks++;
        }
        else{clicks = 1;}

        if(clicks == 2)//remove goal
        {
            parent.removeViewAt(position);
            Goals[position] = "";
        }
        lvCurrentGoals.setOnItemClickListener(this);
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
            String Goal = type +","+ temp + "," + String.valueOf(GoalAtCreate) + ";";
            for(int i = 0; i < Goals.length; i++)
                Goal += Goals[i];
            GoalTracker.edit().putString("CurrentGoal", Goal).apply();
        }
        LoadGoal();

    }

    public void LoadGoal(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        String temp = GoalTracker.getString("CurrentGoal", "");

        lvCurrentGoals.setVisibility(View.VISIBLE);
        if(!temp.equals("")){
            String[] Goal;
            Goals = temp.split(";");
            for(int i = 0; i < Goals.length; i++) {
                Goal = Goals[i].split(",");
                int StepGoal = Integer.valueOf(Goal[1]);
                int OrigSteps = Integer.valueOf(Goal[2]);
                //GoalType: 0 Steps, 1 Calories
                GoalAtCreate = LoadType(Goal[0]);
                int Difference = GoalAtCreate - OrigSteps;

                //Difference = Difference<0?0:Difference;

                if (Difference >= StepGoal) {
                    currentGoal.setText(R.string.GoalCompleted);
                    Goals[i] = "";
                } else {
                    //check to see if goal already exists
                    temp = Goal[0] + ": " + Difference + "/" + StepGoal;


                    Goals[i] = Goal[0] + "," + StepGoal + "," + OrigSteps + ";";
                    adapter.add(temp);
                    //currentGoal.setText("");
                }
            }
            lvCurrentGoals.setAdapter(adapter);
        }
        else {
            currentGoal.setText(R.string.NoActiveGoal);
            lvCurrentGoals.setVisibility(View.INVISIBLE);
        }
    }


}
