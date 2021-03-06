package com.usfit.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.math.MathUtils;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class MainMenuActivity extends AppCompatActivity
        implements SensorEventListener{


    //Sensor
    SensorManager sensorManager;


    //Achievements
    AchievementChecker aCheck;

    //Goals
    GoalChecker gCheck;

    //Variables
    float Stride, CalorieCount, MiKm, weight, calCalcVar;
    int StepCount, DebugTapCount, LastStepCount,winWidth,winHeight;
    boolean tracking, DebugEnabled;
    User currentUser;


    //Preferences
    SharedPreferences sharedPreferences, debugger, timeChecker;
    SharedPreferences.Editor editor, dateEditor;


    //Components
    TextView StepCounter, distanceTextView, calorieTextView;
    Button temp;
    EditText debugNumberTextView;
    RadioGroup measurementRadioGroup;
    DetailManager detailManager;
    ImageView ivTrack,ivPos;

    //Timer
    Handler h = new Handler();
    int delay = 250; //1 second=1000 millisecond, 15*1000=15seconds
    Runnable runnable, dateRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            /////////////////////////Android Stuff////////////////////////////////////
                                        //region Builder
            setContentView(R.layout.activity_main_menu);

            //////////////////////////////////////////////////////////////////////////






        //Item Declarations
        temp = findViewById(R.id.MainMenuDebugStepper);

        distanceTextView = findViewById(R.id.distanceTrackerTextView);

        StepCounter = findViewById(R.id.stepCounterTextView);

        debugNumberTextView = findViewById(R.id.stepNumberTextView);

        measurementRadioGroup = findViewById(R.id.measurementRadioGroup);

        calorieTextView = findViewById(R.id.calorieTextView);

        Rect dimen = new Rect();
        getWindowManager().getDefaultDisplay().getRectSize(dimen);

        winWidth = dimen.width();
        winHeight = dimen.height();


//        detailManager = new DetailManager(this);
    //    detailManager.DrawCrisp(ivTrack,R.drawable.track);
        //Preferences
        ivTrack = (findViewById(R.id.ivTrack));
        sharedPreferences = getSharedPreferences(getString(R.string.SharedStepData), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        timeChecker = getSharedPreferences("com.usfit.stepcounter.DateTime", Context.MODE_PRIVATE);
        dateEditor = timeChecker.edit();


        debugger = getSharedPreferences("com.usfit.stepcounter.debug", Context.MODE_PRIVATE);


        //Assignment

        StepCount = sharedPreferences.getInt("StepCount", 0);

        CalorieCount = sharedPreferences.getFloat("CalorieCount", 0);

        Stride = sharedPreferences.getFloat("StrideLength", -1.0f);

        temp.setVisibility(View.INVISIBLE);

        temp.setEnabled(false);

        DebugTapCount = 5;

        DebugEnabled = false;

        weight = sharedPreferences.getFloat("Weight", 150.0f);

        calCalcVar = 10.7f/20.0f;

        debugNumberTextView.setEnabled(false);

        aCheck = new AchievementChecker();

        gCheck = new GoalChecker(this);

        currentUser = StaticHolderClass.currentUser;

        //Date Check
        DateCheck();


        if(Stride != -1.0f)
        {
            Stride /= 12.0f;
        }



        StepTrack(0);

        //TODO: Save Boolean from user preferences
        tracking = !sharedPreferences.getBoolean("track_on_enter",false);
        toggleTracking(findViewById(R.id.btnTrackSteps));

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



        DetailManager.DrawPlayer(this,StaticHolderClass.currentUser);

    }



    //Tracking
    public void toggleTracking(View v){
        tracking = !tracking;
        String text = "";

        if(tracking){
            text = "Stop Tracking";
        }
        else {
            text = "Start Tracking";
        }

        ((Button)v).setText(text);
    }


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
        debugNumberTextView.setEnabled(true);

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
        if(StepCount != 0) {
            int miles;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                miles = Math.floorDiv(StepCount , 5280);
            }else{
                miles = StepCount/5280;
            }

            if((StepCount % 5280) == 0) {
                String str = "You walked " + miles +" mile!";
                Toast.makeText(this,str,Toast.LENGTH_SHORT);
            }
            if (StepCount % 1000 == 0) {
                tempMoney = 100;
            } else if (StepCount % 100 == 0) {
                tempMoney = 10;
            } else if (StepCount % 10 == 0) {
                tempMoney = 1;
            }

        }

        if(tempMoney > 0) {
            SharedPreferences money = getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);
            tempMoney += money.getInt("MonValue", 0);
            SharedPreferences.Editor tempEditor = money.edit();
            tempEditor.putInt("MonValue", tempMoney);
            tempEditor.apply();
        }
        editor.putInt("StepCount", StepCount);
        editor.apply();
        DistanceCalc();


    }
    public void SetMarker(float pos){
        for(int i = 10000; i != 1;i*=0.1f) {
            while (pos > i) {
                pos--;
            }
        }
        while(pos > 1){
            pos--;
        }
        ImageView ivUserMarker = findViewById(R.id.ivUserMarker);
        View incAvatarMarker = findViewById(R.id.incAvatarMarker);

        float x = (((pos*winWidth)-(winWidth/2))*0.8f);
        x+=winWidth*0.05f;
        ivUserMarker.setX(x);
        incAvatarMarker.setX(x+(winWidth*0.4f));



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

            editor.putFloat("DistanceTrack", MiKm).apply();
            MiKm *= 1000;
            int tempInt = (int)MiKm;
            MiKm = (float)tempInt / 1000.0f;
            String tempString = "Distance: " + MiKm + " " + distanceString;
            distanceTextView.setText(tempString);
        }
        SetMarker(MiKm);

        if((MiKm * 5280)%10 <= 10) {
            int img = (int)MiKm % 10;
            int[] images = {
                    R.drawable.bmp_race_strip_00,
                    R.drawable.bmp_race_strip_01,
                    R.drawable.bmp_race_strip_02,
                    R.drawable.bmp_race_strip_03,
                    R.drawable.bmp_race_strip_04,

                    R.drawable.bmp_race_strip_05,
                    R.drawable.bmp_race_strip_06,
                    R.drawable.bmp_race_strip_07,
                    R.drawable.bmp_race_strip_08,
                    R.drawable.bmp_race_strip_09,
            };
            ivTrack.setImageDrawable(getResources().getDrawable(images[img]));
        }
        CalorieCalc(TempDist);
    }

    public void CalorieCalc(float Distance){
        if(weight != 0.0f){
            float calVar = weight - 100.0f;
            calVar = calVar * calCalcVar + 53.0f;
            float tempTotal = Distance * calVar;

            tempTotal *= 10.0f;
            tempTotal  = (int)tempTotal;
            tempTotal /= 10.0f;
            CalorieCount = tempTotal;
            String tempCal = "Calories: " + tempTotal;
            calorieTextView.setText(tempCal);
            editor.putFloat("CalorieCount", CalorieCount).apply();

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
                AchievementChecker.Check(getApplicationContext());
                gCheck.Check(getApplicationContext());
                DateCheck();
                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);



        Stride = sharedPreferences.getFloat("StrideLength", -1.0f);
        if(Stride != -1.0f)
        {
            Stride /= 12.0f;
        }


        weight = sharedPreferences.getFloat("Weight", 150.0f);


        super.onResume();

    }

    @Override
    protected  void onPause(){
        super.onPause();
        h.removeCallbacks(runnable);


    }

    public void ShowUserPrefs(View v){
        Intent intent = new Intent(MainMenuActivity.this, UserPreferences.class);
        MainMenuActivity.this.startActivity(intent);
    }

    public void StartGoals(View v){
        Intent intent = new Intent(MainMenuActivity.this, GoalsActivity.class);
        MainMenuActivity.this.startActivity(intent);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if((int)event.values[0] != LastStepCount && tracking)
        StepTrack((int)event.values[0] - LastStepCount);


        LastStepCount = (int)event.values[0];


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    public void DateCheck(){
        if(currentUser != null) {
            String tmpDate = timeChecker.getString("LastDateAccessed", "null");
            String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
            if (!tmpDate.equals(date)) {
                int TotalSteps = sharedPreferences.getInt("totalSteps", 0);
                TotalSteps += StepCount;
                currentUser.mTotalSteps = TotalSteps;
                currentUser.UpdateUser();
                StepCount = 0;
                dateEditor.putString("LastDateAccessed", date);
                dateEditor.apply();
                sharedPreferences.edit().putInt("totalSteps", TotalSteps).apply();

            } else {
                int todaySteps, todayDiff;
                todaySteps = sharedPreferences.getInt(date, 0);
                todayDiff = StepCount - todaySteps;
                currentUser.mTotalSteps += todayDiff;

                sharedPreferences.edit().putInt(date, StepCount).apply();
                currentUser.PublishUser();
            }
        }
    }





}
