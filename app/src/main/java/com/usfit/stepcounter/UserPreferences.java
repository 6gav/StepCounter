package com.usfit.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferences extends AppCompatActivity {

    //Declarations
    TextView feetTextView, inchTextView, weightTextView;
    EditText heightInput1, heightInput2, weightInput;
    Switch swImperialMetric,swTrackOnLogEnter;
    Boolean isImperial;
    float Inches;
    SharedPreferences userprefs;
    SharedPreferences.Editor userEditor;

    //Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        //Assigning components
        heightInput1 = findViewById(R.id.heightFirstPlainText);
        heightInput2 = findViewById(R.id.heightSecondPlainText);
        weightInput = findViewById(R.id.weightInput);

        feetTextView = findViewById(R.id.heightFirstTextView);
        inchTextView = findViewById(R.id.inchesTextView);
        weightTextView = findViewById(R.id.weightTextView);

        swImperialMetric = findViewById(R.id.swImperialMetric);
        swTrackOnLogEnter = findViewById(R.id.swTrackOnLogEnter);


        userprefs = getSharedPreferences(getString(R.string.SharedStepData), Context.MODE_PRIVATE);
        userEditor = userprefs.edit();
        userEditor.apply();
        //Variable Initialization
        isImperial = userprefs.getBoolean("IsImperial",true);
        SwitchValue();



        //Functions









    }




    //OnClicks
    public void OnTrackEnterClick(Switch sw){
    }


    public void ApplyPrefs(View v){
        ApplyHeight(v);
        ApplyWeight(v);

        //Switch button data

        userprefs.edit().putBoolean("IsImperial",isImperial).apply();
    }
    private void ApplyHeight(View v){
        if(isImperial){
            String tempFeet = heightInput1.getText().toString();
            String tempInches = heightInput2.getText().toString();
            if(!tempFeet.equals("") && !tempInches.equals("")) {
                Inches = Float.valueOf(tempInches);
                Inches += (Float.valueOf(tempFeet) * 12);
                float Stride = Inches * 0.413f;
                userEditor.putFloat("StrideLength", Stride).apply();
            }
            else
                Toast.makeText(this, "Please enter both height values", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String tempCm = heightInput1.getText().toString();
            if (!tempCm.equals("")) {
                Inches = (Float.valueOf(tempCm)/2.54f);
                float Stride = Inches * 0.413f;
                userEditor.putFloat("StrideLength", Stride).apply();

            }
            else
                Toast.makeText(this, "Please enter a height value", Toast.LENGTH_SHORT).show();
        }
    }

    private void ApplyWeight(View v){
        String weightText = weightInput.getText().toString();
        if(!weightText.equals("")) {
            if (isImperial) {
                String tempLBS = weightInput.getText().toString();
                float tempWeight = Float.valueOf(tempLBS);
                userEditor.putFloat("Weight", tempWeight).apply();
            } else {
                String tempKG = weightInput.getText().toString();
                float tempWeight = Float.valueOf(tempKG);
                tempWeight *= 2.204622f;
                userEditor.putFloat("Weight", tempWeight).apply();
            }
        }
        else{
            Toast.makeText(this, "Please enter a weight value", Toast.LENGTH_SHORT).show();
        }
    }

    public void RadioListener(View v){
        SwitchValue();
    }
    private void SwitchValue(){
        if(!swImperialMetric.isChecked()){
            feetTextView.setText(R.string.FeetTextAbbreviation);
            weightTextView.setText(R.string.InchTextAbbreviation);
            inchTextView.setVisibility(View.VISIBLE);
            heightInput2.setVisibility(View.VISIBLE);
            isImperial = true;
        }
        else
        {
            feetTextView.setText(R.string.CmTextAbbreviation);
            weightTextView.setText(R.string.KgTextAbbreviation);
            inchTextView.setVisibility(View.INVISIBLE);
            heightInput2.setVisibility(View.INVISIBLE);
            isImperial = false;
        }
    }

    public void ToFriends(View v){
        Intent friendsIntent = new Intent(this, FriendsActivity.class);
        startActivity(friendsIntent);
    }
    public void ToAvatarCustomize(View v){
        Intent n = new Intent(this,AvatarCustomizationActivity.class);
        startActivity(n);
    }


}
