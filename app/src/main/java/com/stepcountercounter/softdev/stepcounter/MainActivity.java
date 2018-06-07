package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private SensorManager sensorManager;
    private TextView count, count2, count3;
    private int StepCounts, TapCount;
    boolean activityRunning, DebugMode;
    private Button debugStepButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TapCount = 4;
        DebugMode = false;
        setContentView(R.layout.activity_main);
        debugStepButton = (Button)findViewById(R.id.debugAddStepsButton);

        count = (TextView)findViewById(R.id.countTextView);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

    }

    private void DebugEnable(){
        debugStepButton.setVisibility(View.VISIBLE);
        debugStepButton.setClickable(true);
        debugStepButton.setEnabled(true);
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
        else
        {

        }
    }


    protected void onResume(){
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, sensorManager.SENSOR_DELAY_UI);

        }
        else
        {
            Toast.makeText(this, "Count sensor not available", Toast.LENGTH_LONG).show();;
        }
    }

    public void onPause(){
        super.onPause();
        activityRunning = false;
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


}
