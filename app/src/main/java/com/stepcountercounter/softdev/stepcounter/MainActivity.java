package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private SensorManager sensorManager;
    private TextView count, count2, count3;
    private int StepCounts, TapCount, MonValue,StepsUntilMon = 10;
    boolean activityRunning, DebugMode;
    private Button debugStepButton;
    private SharedPreferences MoneyPref;
    private SharedPreferences.Editor MoneyEditor;
    private TextView MoneyCounterTextView;

    TextView[] goalTextView = new TextView[3];
    FitGoal[] goals = new FitGoal[3];

    Handler h = new Handler();
    int delay = 250; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.stepdata", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TapCount = 4;
        DebugMode = false;
        debugStepButton = findViewById(R.id.debugAddStepsButton);

        count = findViewById(R.id.countTextView);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        MoneyPref = getSharedPreferences("com.stepcountercounter.marketplace", Context.MODE_PRIVATE);
        MonValue = MoneyPref.getInt("MonValue",0);
        MoneyEditor = MoneyPref.edit();
        MoneyEditor.apply();
        MoneyCounterTextView = findViewById(R.id.tvMonValueInfo);
        //((TextView)findViewById(R.id.tvGoal1)).setText(sharedPreferences.getString("AllGoals",""));
        InitGoals();
    }
    void InitGoals(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.stepdata", 0);

        String[] s = sharedPreferences.getString("AllGoals","").split("\n    ");
        for(int i = 0; i < s.length-1; i++){
            if(!s[i].contentEquals("    ") && !s[i].contentEquals("") && s[i] != null)
            goals [i] = AddGoal(goalTextView[i]);
        }
    }
    private void DebugEnable(){
        debugStepButton.setVisibility(View.VISIBLE);
        debugStepButton.setClickable(true);
        debugStepButton.setEnabled(true);
    }


    public void MoneyUpdate(View v){
        TextView MoneyText = findViewById(R.id.tvMonValueInfo);
        String tempMoneytext = "X " + MoneyPref.getInt("MonValue", 0);
        MoneyText.setText(tempMoneytext);
    }

    public void DebugClicker(View v){
        if(TapCount > 0) {



            if (TapCount <= 4) {
                String tapText = "You are " + TapCount + " taps away from debug mode.";
                Toast.makeText(this, tapText, Toast.LENGTH_SHORT).show();
            }
            TapCount--;
        }
        else if(TapCount == 0)
        {
            DebugMode = true;
            DebugEnable();
            Toast.makeText(this, "You are now in debug mode.", Toast.LENGTH_SHORT).show();
            TapCount--;
        }

    }


    protected void onResume(){
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        CheckGoal();
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(this, "Count sensor not available", Toast.LENGTH_LONG).show();
        }

        h.postDelayed(new Runnable() {
            public void run() {
                String temp = "X" + MoneyPref.getInt("MonValue", 0);
                MoneyCounterTextView.setText(temp);
                runnable=this;
                h.postDelayed(runnable, delay);
            }
        }, delay);

    }

    public void onPause(){
        super.onPause();
        activityRunning = false;
        h.removeCallbacks(runnable);
    }


    public void sendMessage(View v){
        Intent newActivity = new Intent(this, MainMenuActivity.class);
        startActivity(newActivity);



    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(activityRunning){
            String tempText = "Steps: " + String.valueOf(event.values[0]);
            count.setText(tempText);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void ToAchievements(View v){
        Intent n = new Intent(this,AchievementsActivity.class);
        startActivity(n);
    }
    public void ToAvatar(View v){
        Intent n = new Intent(this,AvatarActivity.class);
        startActivity(n);
    }
    public void ToShop(View v){
        Intent n = new Intent(this,ShopActivity.class);
        startActivity(n);
    }




    //////////////////////////////////////////////// Save Data ////////////////////////////////////////////////
/*
public void SaveTest(View v) {
    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.stepdata", 0);
    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putInt("Steps",3);
    editor.putString("Saved","Yaes!");

    editor.apply();
}*/


/*
public void LoadTest(View v) {
    TextView t = findViewById(R.id.tvTestSave);
    t.setText(StrPreferences("Saved"));
}*/


    //////////////////////////////////////////////// Save Data ////////////////////////////////////////////////


    //////////////////////////////////////////////// Fit Goals ////////////////////////////////////////////////
    public FitGoal AddGoal(){
        EditText desc = findViewById(R.id.etGoalDescription);
        Boolean comp = ((CheckBox)findViewById(R.id.cbComplete)).isChecked();
        String s = desc.getText().toString();

        return new FitGoal(s,comp);
    }

    public FitGoal AddGoal(TextView t){
        EditText desc = findViewById(R.id.etGoalDescription);
        Boolean comp = ((CheckBox)findViewById(R.id.cbComplete)).isChecked();
        String s = desc.getText().toString();
        FitGoal f = new FitGoal(s,comp);
        f.SetMyView(t);
        return f;
    }

    public void CheckGoal(View v){
        CheckGoal();
    }

    public void CheckGoal() {
        /*EditText desc = findViewById(R.id.etGoalDescription);
        CheckBox comp = findViewById(R.id.cbComplete);
        Button b = findViewById(R.id.btnAddGoal);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.stepdata", 0);
        int count = 0;//sharedPreferences.getInt("NumGoals", 0);
        for(int i = 0; i < 3; ++i){
            if(goals[i] != null)count++;
        }
        String goals = sharedPreferences.getString("AllGoals","");
        String s = desc.getText().toString();
        String[] AllGoals = goals.split("\n");
        if (count <= 3) {
            if (s != "") {
                FitGoal f = new FitGoal(s, comp.isChecked());
                count++;
                SharedPreferences.Editor e = sharedPreferences.edit();
                goals += s+'\n';
                e.putString("AllGoals",goals);
                e.putInt("NumGoals",count);
                e.apply();
                f.getMyView().setText(goals);
            }
        }else{
        Toast.makeText(this,"Maximum of 3 goals, please complete some existing goals",Toast.LENGTH_SHORT).show();

        }*/
    }
    }



    class FitGoal{
    String goal_description;
    boolean complete;
    TextView MyView;

        public FitGoal(String g, boolean c){
            goal_description = g;
            complete = c;
        }
        public void SetMyView(TextView textView){
            MyView = textView;
        }

        public TextView getMyView() {
            return MyView;
        }

        public void LoadGoal(String s){
        //0,I want to walk 1000 steps this week
        complete = (s.charAt(0) == 1);//bool for complete
         //initialize the description, then add each char individually after comma
        goal_description = "";
        for(int i = 2; i < s.length();++i) {
            goal_description += s.charAt(i);
        }
        MyView.setText(goal_description);
    }

    public String GoalToString(){
        return complete+","+goal_description;
    }
}
    //////////////////////////////////////////////// Fit Goals ////////////////////////////////////////////////

