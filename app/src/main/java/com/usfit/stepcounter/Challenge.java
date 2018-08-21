package com.usfit.stepcounter;


import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Challenge {
    public String cKey, mType, mAmount, mReward, mTime;

    public float AmountAtChallengeAccept;

    public UserInfoPackage mSender;

    public Challenge(){

    }

    public Challenge(String cKey, String mType, String mAmount, String mReward, String mTime, UserInfoPackage mSender) {
        this.mType = mType;
        this.mAmount = mAmount;
        this.mReward = mReward;
        this.mTime = mTime;
        this.mSender = mSender;
        this.cKey = cKey;
    }

    public static void CheckChallenge(Context mContext, Challenge challenge){
        SharedPreferences chPref;

        switch(challenge.mType){
            case "Distance":
                chPref = mContext.getSharedPreferences(mContext.getString(R.string.SharedStepData), Context.MODE_PRIVATE);

                Boolean isImperial = chPref.getBoolean("IsImperial", true);

                Float currDistance = chPref.getFloat("Distance", 0.0f);

                Float difference = challenge.AmountAtChallengeAccept - currDistance;

                if(difference >= (float)Integer.valueOf(challenge.mAmount)){
                    CompleteChallenge(mContext, challenge);
                }

                break;
        }
    }

    private static void CompleteChallenge(Context mContext, Challenge challenge) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference() ;

    }
}
