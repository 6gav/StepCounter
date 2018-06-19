package com.stepcountercounter.softdev.stepcounter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AvatarActivity extends AppCompatActivity {

    enum ClothingType{
        HAT,
        TOP,
        BOT,
        FOT,
        PET,
    }

    ImageView ivTop,ivBottom,ivFeet;
    SharedPreferences preferences;

    //region arrays
    int[] top = {
            R.drawable.outfit_t00,
            R.drawable.outfit_t01,
            R.drawable.outfit_t02,

            R.drawable.outfit_t03,
            R.drawable.outfit_t04,
            R.drawable.outfit_t05,

            R.drawable.outfit_t06,
            R.drawable.outfit_t07,
            R.drawable.outfit_t08,

            R.drawable.outfit_t09,
            R.drawable.outfit_t10,
            R.drawable.outfit_t11,

            R.drawable.outfit_t12,
            R.drawable.outfit_t13,
            R.drawable.outfit_t14,

            R.drawable.outfit_t15,
            R.drawable.outfit_t16,
            R.drawable.outfit_t17,
    };

    int[] bottom = {
            R.drawable.outfit_b1,
            R.drawable.outfit_b1,
            R.drawable.outfit_b1
    };

    int[] footwear = {
            R.drawable.outfit_f1,
            R.drawable.outfit_f1,
            R.drawable.outfit_f1
    };
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        preferences = getApplicationContext().getSharedPreferences("com.stepcountercounter.marketplace",MODE_PRIVATE);

        ivTop = findViewById(R.id.ivTop);
        ivBottom = findViewById(R.id.ivBottom);
        ivFeet = findViewById(R.id.ivFootwear);

        ivTop.setImageDrawable(getDrawable(preferences.getInt("A_TOP",R.drawable.outfit_t00)));
        ivBottom.setImageDrawable(getDrawable(preferences.getInt("A_BOT",R.drawable.outfit_b1)));
        ivFeet.setImageDrawable(getDrawable(preferences.getInt("A_FOT",R.drawable.outfit_f1)));



    }
    public void OnTopPress(View v){
        ImageView I = findViewById(R.id.ivTop);
        int vis = I.getVisibility();
        if(vis == 0){vis = 4;}
        else{vis = 0;}
        I.setVisibility(vis);

    }
    public void OnBottomPress(View v){
        ImageView I = findViewById(R.id.ivBottom);
        int vis = I.getVisibility();
        if(vis == 0){vis = 4;}
        else{vis = 0;}
        I.setVisibility(vis);
    }
    public void OnFootwearPress(View v){
        ImageView I = findViewById(R.id.ivFootwear);
        int vis = I.getVisibility();
        if(vis == 0){vis = 4;}
        else{vis = 0;}
        I.setVisibility(vis);

    }


}
