package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class UserPreferences extends AppCompatActivity {

    //Declarations
    RadioButton metricRadio, imperialRadio;
    TextView feetTextView, inchTextView;
    EditText heightInput1, heightInput2;
    Boolean isImperial;
    float Inches, cm;


    //Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        //Assigning components
        heightInput1 = findViewById(R.id.heightFirstPlainText);
        heightInput2 = findViewById(R.id.heightSecondPlainText);
        feetTextView = findViewById(R.id.heightFirstTextView);
        inchTextView = findViewById(R.id.inchesTextView);
        metricRadio = findViewById(R.id.metricRadioButton);
        imperialRadio = findViewById(R.id.imperialRadioButton);



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
                getSharedPreferences("com.stepcountercounter.UserPrefs", Context.MODE_PRIVATE).edit().putFloat("StrideLength", Stride).apply();
            }
        }
        else
        {
            String tempCm = heightInput1.getText().toString();
            if (!tempCm.equals("")) {
                Inches = (Float.valueOf(tempCm)/2.54f);
                float Stride = Inches * 0.413f;
                getSharedPreferences("com.stepcountercounter.UserPrefs", Context.MODE_PRIVATE).edit().putFloat("StrideLength", Stride).apply();

            }
        }
    }


    public void RadioListener(View v){
        if(imperialRadio.isChecked()){
            feetTextView.setText("ft");
            inchTextView.setVisibility(View.VISIBLE);
            heightInput2.setVisibility(View.VISIBLE);
            isImperial = true;
        }
        else
        {
            feetTextView.setText("cm");
            inchTextView.setVisibility(View.INVISIBLE);
            heightInput2.setVisibility(View.INVISIBLE);
            isImperial = false;
        }
    }
}
