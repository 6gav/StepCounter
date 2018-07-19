package com.usfit.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


public class AchievementChecker{

    public static void Check(Context mContext){

        String[] ResourceArray;
        ResourceArray = mContext.getResources().getStringArray(R.array.aFull);

        for(int i = 0; i < ResourceArray.length; i++){
            String[] tempAch = ResourceArray[i].split(":");

            HandleAchievement(tempAch, mContext);
        }

    }

    //Name    Type    Amount  Reward    ID
    //  0       1       2       3       4
    private static void HandleAchievement(String[] achParts, Context mContext){
        String aType, aAmount, aReward, aID;



        //Splitting into parts
        aType = achParts[1];
        aAmount = achParts[2];
        aReward = achParts[3];
        aID = achParts[4];


        //Preferences
        SharedPreferences achPrefs = mContext.getSharedPreferences("com.usfit.stepcounter.AchievementProgress", Context.MODE_PRIVATE);



        //Check if achievement has already been completed
        if(!achPrefs.getBoolean(aID, false)) {

            //Check what type of achievement
            switch (aType) {
                //region Steps
                case "Steps":

                    int nAmount = Integer.valueOf(aAmount);
                    SharedPreferences StepData = mContext.getSharedPreferences(mContext.getString(R.string.SharedStepData), Context.MODE_PRIVATE);

                    int stepCount = StepData.getInt("StepCount", 0);

                    if (stepCount >= nAmount) {

                        SharedPreferences marketPrefs = mContext.getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);

                        int pCurrency = marketPrefs.getInt("MonValue", 0);
                        pCurrency += Integer.valueOf(aReward);

                        marketPrefs.edit().putInt("MonValue", pCurrency).apply();

                        achPrefs.edit().putBoolean(aID, true).apply();
                        String toastString = "You completed the " + achParts[0] + " Achievement! " + aReward + " currency added!";
                        Toast.makeText(mContext, toastString, Toast.LENGTH_LONG).show();
                    }
                    break;
                //endregion

                //region Calories
                case "Calories":
                    
                    break;
                //endregion
            }
        }

    }


}
