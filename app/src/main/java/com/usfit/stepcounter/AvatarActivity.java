package com.usfit.stepcounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    };

    int[] bottom = {
            R.drawable.outfit_b00,
            R.drawable.outfit_b01,
            R.drawable.outfit_b02,
            R.drawable.outfit_b03,
            R.drawable.outfit_b04,
            R.drawable.outfit_b05,
            R.drawable.outfit_b06,
            R.drawable.outfit_b07,
            R.drawable.outfit_b08,
            R.drawable.outfit_b09,
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

        preferences = getApplicationContext().getSharedPreferences("com.usfit.stepcounter.marketplace",MODE_PRIVATE);

        ivTop = findViewById(R.id.ivTop);
        ivBottom = findViewById(R.id.ivBottom);
        ivFeet = findViewById(R.id.ivFootwear);


        int x = preferences.getInt("A_TOP", R.drawable.outfit_t00);
        Drawable temp = getDrawable(x);


        UpdateOutfit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateOutfit();
    }

    void UpdateOutfit(){
        ivTop.setImageDrawable(getDrawable(preferences.getInt("A_TOP",R.drawable.outfit_t00)));
        ivBottom.setImageDrawable(getDrawable(preferences.getInt("A_BOT",R.drawable.outfit_b00)));
        ivFeet.setImageDrawable(getDrawable(preferences.getInt("A_FOT",R.drawable.outfit_f1)));
    }

    public void ToInventory(View v){
        Intent I = new Intent(this, Inventory.class);
        startActivity(I);
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
