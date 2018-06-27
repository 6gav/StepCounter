package com.stepcountercounter.softdev.stepcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Inventory extends AppCompatActivity {

    //region arrays
    int[] cost = {
            10,
            20,
            25,

            25,
            25,
            30,

            50,
            100,
            125,

            135,
            150,
            160,

            160,
            175,
            180,

            200,
            200,
            200,//shirts

            10,
            10,
            15,

            20,
            20,
            25,

            25,
            50,//pants
            30,

            60,
            90,

            10,
            10,
            10//footwear

            //hats
    };

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
            R.drawable.outfit_b10
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
        setContentView(R.layout.activity_inventory);
    }
}
