package com.usfit.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferences extends AppCompatActivity {

    //Declarations
    RadioButton metricRadio, imperialRadio;
    TextView feetTextView, inchTextView, weightTextView;
    EditText heightInput1, heightInput2, weightInput;
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

        metricRadio = findViewById(R.id.metricRadioButton);
        imperialRadio = findViewById(R.id.imperialRadioButton);


        userprefs = getSharedPreferences(getString(R.string.SharedStepData), Context.MODE_PRIVATE);
        userEditor = userprefs.edit();
        userEditor.apply();
        //Variable Initialization
        isImperial = true;




        //Functions









    }




    //OnClicks
    public void ApplyPreferences(View v){
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

    public void ApplyWeight(View v){
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
        if(imperialRadio.isChecked()){
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
}
