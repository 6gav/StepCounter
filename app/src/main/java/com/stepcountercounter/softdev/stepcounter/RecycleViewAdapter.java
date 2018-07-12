package com.stepcountercounter.softdev.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{

    private ArrayList<String> achievementHeading = new ArrayList<>();
    private ArrayList<String> achievementDescription = new ArrayList<>();
    private ArrayList<String> achievementAward = new ArrayList<>();
    private ArrayList<String> achievementReq = new ArrayList<>();
    private ArrayList<String> achievementIDS = new ArrayList<>();
    private Context mContext;



    //Preferences
    SharedPreferences preferences;
    SharedPreferences AchievementsPref;
    SharedPreferences moneyPrefs;




    //Variables
    int StepCount;



    public RecycleViewAdapter(ArrayList<String> achievementHeading, ArrayList<String> achievementDescription, ArrayList<String> achievementAward, ArrayList<String> achievementReq,  ArrayList<String> achievementID, Context mContext) {
        this.achievementHeading = achievementHeading;
        this.achievementDescription = achievementDescription;
        this.achievementAward = achievementAward;
        this.achievementReq = achievementReq;
        this.achievementIDS = achievementID;
        this.mContext = mContext;
        preferences = mContext.getSharedPreferences("com.stepcountercounter.stepdata", Context.MODE_PRIVATE);
        AchievementsPref = mContext.getSharedPreferences("com.stepcountercounter.AchievementProgress", Context.MODE_PRIVATE);

        moneyPrefs = mContext.getSharedPreferences("com.stepcountercounter.marketplace", Context.MODE_PRIVATE);

        StepCount = preferences.getInt("StepCount", 0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.aHeading.setText(achievementHeading.get(position));
        holder.aDesc.setText(achievementDescription.get(position));
        holder.aAward.setText(achievementAward.get(position));
        String testResult = CheckAchievements(position);
        holder.aCount.setText(testResult);

    }

    @Override
    public int getItemCount() {
        return achievementHeading.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Declarations
        TextView aHeading, aDesc, aAward, aCount;
        ConstraintLayout aLayout;



        public ViewHolder(View itemView) {
            super(itemView);

            //Assign components
            aHeading = itemView.findViewById(R.id.achievementHeading);
            aDesc = itemView.findViewById(R.id.achievementDescription);
            aAward = itemView.findViewById(R.id.achievementAward);
            aCount = itemView.findViewById(R.id.achievementRequirement);
            aLayout = itemView.findViewById(R.id.list_view_layout);


        }
    }

    private String CheckAchievements(int position){
        String Req = achievementReq.get(position);
        String Progress = "";
        String[] ReqParts = Req.split(":");

        String tempID = achievementIDS.get(position);
        boolean Complete = AchievementsPref.getBoolean(tempID, false);

        if(!Complete) {
            switch (ReqParts[0]) {
                case "Steps":
                    int tempSteps = Integer.valueOf(ReqParts[1]);
                    if (StepCount < tempSteps)
                        Progress = StepCount + " / " + ReqParts[1];
                    else {
                        Progress = "Complete!";
                        AchievementsPref.edit().putBoolean(tempID, true).apply();
                        int tempMoney = moneyPrefs.getInt("MonValue", 0);
                        tempMoney += Integer.valueOf(ReqParts[2]);

                        moneyPrefs.edit().putInt("MonValue", tempMoney).apply();
                    }
                    break;
                case "Calories:":

                    break;
            }
        }
        else
            Progress = "Complete!";
        return Progress;
    }

}
