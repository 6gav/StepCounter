package com.usfit.stepcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AchievementsActivity extends AppCompatActivity {

    //Variables
    private ArrayList<String> mHeadings = new ArrayList<>();
    private ArrayList<String> mDescs = new ArrayList<>();
    private ArrayList<String> mAwards = new ArrayList<>();
    private ArrayList<String> mReqs = new ArrayList<>();
    private ArrayList<String> mIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        Init();
    }

    private void Init(){
        //                                0     1    2      3    4
        //    //New Achievement Syntax - Name:Type:Amount:Reward:ID
        String[] resFull = getResources().getStringArray(R.array.aFull),
                achievement;
        String description;
        for(int i = 0; i < resFull.length; i++){
            achievement = resFull[i].split(":");
            description = "";



            mHeadings.add(achievement[0]);
            mDescs.add(description);
            mAwards.add(achievement[3]+ " Currency");
            mReqs.add(achievement[1]+":"+achievement[2]+":"+achievement[3]);
            mIDs.add(achievement[4]);
        }
        InitRecycler();
    }

    private void InitRecycler(){
        RecyclerView recyclerView = findViewById(R.id.achievementRecyclerView);
        RecycleViewAdapter adapter = new RecycleViewAdapter(mHeadings, mDescs, mAwards, mReqs, mIDs, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    private void CheckAchievement(){

    }
}
