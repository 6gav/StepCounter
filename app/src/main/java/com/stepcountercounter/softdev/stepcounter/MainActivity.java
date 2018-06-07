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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private SensorManager sensorManager;
    private TextView count;
    boolean activityRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = findViewById(R.id.countTextView);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);





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
            Toast.makeText(this, "Count sensor not available", Toast.LENGTH_LONG).show();
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
            count.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void ToAchievements(View v){
        Intent n = new Intent(this,MainMenuActivity.class);
        startActivity(n);
    }
    public void ToAvatar(View v){
        Intent n = new Intent(this,AvatarActivity.class);
        startActivity(n);
    }
}
