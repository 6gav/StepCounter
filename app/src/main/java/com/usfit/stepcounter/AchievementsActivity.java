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
        String[] resHeading = getResources().getStringArray(R.array.aHeadings);
        String[] resDesc = getResources().getStringArray(R.array.aDescs);
        String[] resAward = getResources().getStringArray(R.array.aAwards);
        String[] resReq = getResources().getStringArray(R.array.aReq);
        String[] resID = getResources().getStringArray(R.array.aID);
        for(int i = 0; i < resHeading.length; i++){
            mHeadings.add(resHeading[i]);
            mDescs.add(resDesc[i]);
            mAwards.add(resAward[i]);
            mReqs.add(resReq[i]);
            mIDs.add(resID[i]);
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