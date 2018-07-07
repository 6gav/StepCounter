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



public class MainActivity extends AppCompatActivity {


    private TextView count, count2, count3;
    private int StepCounts, TapCount, MonValue, StepsUntilMon = 10;
    boolean activityRunning, DebugMode;
    private Button debugStepButton;
    private SharedPreferences MoneyPref;
    private SharedPreferences.Editor MoneyEditor;
    private TextView MoneyCounterTextView;


    Handler h = new Handler();
    int delay = 250; //1 second=1000 milliseconds, 15*1000=15seconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.stepdata", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TapCount = 4;
        DebugMode = false;

        count = findViewById(R.id.countTextView);

        MoneyPref = getSharedPreferences("com.stepcountercounter.marketplace", Context.MODE_PRIVATE);
        MonValue = MoneyPref.getInt("MonValue", 0);
        MoneyEditor = MoneyPref.edit();
        MoneyCounterTextView = findViewById(R.id.tvMonValueInfo);
        //((TextView)findViewById(R.id.tvGoal1)).setText(sharedPreferences.getString("AllGoals",""));
    }



    private void DebugEnable() {
        debugStepButton.setVisibility(View.VISIBLE);
        debugStepButton.setClickable(true);
        debugStepButton.setEnabled(true);
    }


    public void MoneyUpdate(View v) {
        TextView MoneyText = findViewById(R.id.tvMonValueInfo);
        String tempMoneytext = "X " + MoneyPref.getInt("MonValue", 0);
        MoneyText.setText(tempMoneytext);
    }

    public void DebugClicker(View v) {
        if (TapCount > 0) {


            if (TapCount <= 4) {
                String tapText = "You are " + TapCount + " taps away from debug mode.";
                Toast.makeText(this, tapText, Toast.LENGTH_SHORT).show();
            }
            TapCount--;
        } else if (TapCount == 0) {
            DebugMode = true;
            DebugEnable();
            Toast.makeText(this, "You are now in debug mode.", Toast.LENGTH_SHORT).show();
            TapCount--;
        }

    }


    protected void onResume() {
        super.onResume();
        activityRunning = true;



        h.postDelayed(new Runnable() {
            public void run() {
                String temp = "X " + MoneyPref.getInt("MonValue", 0);
                MoneyCounterTextView.setText(temp);
                runnable = this;
                h.postDelayed(runnable, delay);
            }
        }, delay);

    }

    public void onPause() {
        super.onPause();
        activityRunning = false;
        h.removeCallbacks(runnable);
    }


    public void sendMessage(View v) {
        Intent newActivity = new Intent(this, MainMenuActivity.class);
        startActivity(newActivity);


    }


    public void ToAchievements(View v) {
        Intent n = new Intent(this, AchievementsActivity.class);
        startActivity(n);
    }

    public void ToAvatar(View v) {
        Intent n = new Intent(this, AvatarActivity.class);
        startActivity(n);
    }

    public void ToShop(View v) {
        Intent n = new Intent(this, ShopActivity.class);
        startActivity(n);
    }

    public void ToGoals(View v) {
        Intent n = new Intent(this, GoalsActivity.class);
        startActivity(n);
    }
    public void ToInventory(View v) {
        Intent n = new Intent(this, Inventory.class);
        startActivity(n);
    }
    public void ToPreferences(View v){
        Intent n = new Intent(this, UserPreferences.class);
        startActivity(n);
    }
}
