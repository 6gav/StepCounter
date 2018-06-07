package com.stepcountercounter.softdev.stepcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AvatarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
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
