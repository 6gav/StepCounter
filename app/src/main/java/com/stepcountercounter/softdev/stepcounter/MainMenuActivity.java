package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener{


    //Sensor
    SensorManager sensorManager;


    //Variables
    float Stride, MiKm, weight, calCalcVar;
    int StepCount, DebugTapCount, LastStepCount;
    boolean tracking, DebugEnabled;


    //Preferences
    SharedPreferences sharedPreferences, debugger, timeChecker;
    SharedPreferences.Editor editor, dateEditor;


    //Components
    TextView StepCounter, distanceTextView, calorieTextView;
    Button temp;
    EditText debugNumberTextView;
    RadioGroup measurementRadioGroup;


    //Timer
    Handler h = new Handler();
    int delay = 250; //1 second=1000 millisecond, 15*1000=15seconds
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            /////////////////////////Android Stuff////////////////////////////////////
                                        //region Builder
            setContentView(R.layout.activity_main_menu);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            //endregion  -
            //////////////////////////////////////////////////////////////////////////






        //Item Declarations
        temp = findViewById(R.id.MainMenuDebugStepper);

        distanceTextView = findViewById(R.id.distanceTrackerTextView);

        StepCounter = findViewById(R.id.stepCounterTextView);

        debugNumberTextView = findViewById(R.id.stepNumberTextView);

        measurementRadioGroup = findViewById(R.id.measurementRadioGroup);

        calorieTextView = findViewById(R.id.calorieTextView);


        //Preferences
        sharedPreferences = getSharedPreferences("com.stepcountercounter.stepdata", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        timeChecker = getSharedPreferences("com.stepcountercounter.DateTime", Context.MODE_PRIVATE);
        dateEditor = timeChecker.edit();


        debugger = getSharedPreferences("com.stepcountercounter.debug", Context.MODE_PRIVATE);


        //Assignment
        StepCount = sharedPreferences.getInt("StepCount", 0);

        Stride = sharedPreferences.getFloat("StrideLength", -1.0f);

        temp.setVisibility(View.INVISIBLE);

        temp.setEnabled(false);

        DebugTapCount = 5;

        DebugEnabled = false;

        weight = sharedPreferences.getFloat("Weight", 0.0f);

        calCalcVar = 10.7f/20.0f;


        //Date Check
        String tmpDate = timeChecker.getString("LastDateAccessed", "null");
        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        if(!tmpDate.equals(date)){
            StepCount = 0;
            dateEditor.putString("LastDateAccessed", date);
            dateEditor.apply();
        }




        if(Stride != -1.0f)
        {
            Stride /= 12.0f;
        }



        StepTrack(0);

        tracking = false;

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            Toast.makeText(this, "No Sensor Available.", Toast.LENGTH_SHORT).show();
        }

        DebugEnabled = debugger.getBoolean("debugEnabled", false);
        if(DebugEnabled){
            DebugEnable();
        }
    }



    //Tracking
    public void startTracking(View v){ tracking = true; }
    public void stopTracking(View v){ tracking = false; }



    //Debug
    public void AddSteps(View v){
        if(DebugEnabled){
            String temp = debugNumberTextView.getText().toString();
            if(!temp.equals("")) {
                int textSteps = Integer.valueOf(temp);
                StepTrack(textSteps);
            }
        }
    }
    public void DebugEnable(){
        temp.setVisibility(View.VISIBLE);
        temp.setEnabled(true);
        temp.setClickable(true);
        debugNumberTextView.setVisibility(View.VISIBLE);

    }
    public void DebugTry(View v){
        if(!DebugEnabled) {
            if (DebugTapCount > 0) {
                DebugTapCount--;

            } else if (DebugTapCount == 0) {
                debugger.edit().putBoolean("debugEnabled", true).apply();
                DebugEnabled = true;
                DebugEnable();
            }
        }
    }


    //StepTracker
    public void StepTrack(int x){
        StepCount+=x;
        int tempMoney = 0;
        if(StepCount % 1000 == 0){
            tempMoney = 100;
        }
        else if(StepCount % 100 == 0){
            tempMoney = 10;
        }
        else if(StepCount % 10 == 0){
            tempMoney = 1;
        }
        if(tempMoney > 0) {
            SharedPreferences money = getSharedPreferences("com.stepcountercounter.marketplace", Context.MODE_PRIVATE);
            tempMoney += money.getInt("MonValue", 0);
            SharedPreferences.Editor tempEditor = money.edit();
            tempEditor.putInt("MonValue", tempMoney);
            tempEditor.apply();
        }
        editor.putInt("StepCount", StepCount);
        editor.apply();
        DistanceCalc();

    }

    public void RadioSwitch(View v){ DistanceCalc(); }

    public void DistanceCalc() {
        RadioButton tempRadio = findViewById(measurementRadioGroup.getCheckedRadioButtonId());
        String distanceString = tempRadio.getText().toString();

        float TempDist = -1.0f;
        if(Stride == -1.0f){
            distanceTextView.setText(R.string.HeightNotSet);
            Stride = -2.0f;
        }
        else if(Stride > 0.0f)
        {

            MiKm = Stride*StepCount/5280.0f;
            TempDist = MiKm;
            if(distanceString.equals("Km"))
                MiKm *= 1.609344f;
            MiKm *= 1000;
            int tempInt = (int)MiKm;
            MiKm = (float)tempInt / 1000.0f;
            String tempString = "Distance: " + MiKm + " " + distanceString;
            distanceTextView.setText(tempString);
        }
        CalorieCalc(TempDist);
    }

    public void CalorieCalc(float Distance){
        if(weight != 0.0f){
            float calVar = weight - 100.0f;
            calVar = calVar * calCalcVar + 53.0f;
            float tempTotal = Distance * calVar;

            tempTotal *= 10.0f;
            tempTotal = (int)tempTotal;
            tempTotal /=10.0f;
            String tempCal = "Calories: " + tempTotal;
            calorieTextView.setText(tempCal);
        }
        else if(weight == 0.0f)
        {
            calorieTextView.setText(R.string.WeightNotSet);
            weight = -1.0f;
        }

    }

    @Override
    protected void onResume(){

        DistanceCalc();

        h.postDelayed(new Runnable() {
            public void run() {
                String tempStepText = "Steps: " + String.valueOf(StepCount);
                StepCounter.setText(tempStepText);
                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

        Stride = sharedPreferences.getFloat("StrideLength", -1.0f);
        if(Stride != -1.0f)
        {
            Stride /= 12.0f;
        }


        weight = sharedPreferences.getFloat("Weight", 0.0f);


        super.onResume();

    }

    @Override
    protected  void onPause(){
        h.removeCallbacks(runnable);
        super.onPause();


    }

    public void ShowUserPrefs(View v){
        Intent intent = new Intent(MainMenuActivity.this, UserPreferences.class);
        MainMenuActivity.this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    ///////////////////DO NOT USE THIS//////////////////////
    //////////////WILL BE REMOVED IN REDESIGN///////////////
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    ////////////////////////////////////////////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {
        if((int)event.values[0] != LastStepCount && tracking)
        StepTrack(1);


        LastStepCount = (int)event.values[0];


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }





}
