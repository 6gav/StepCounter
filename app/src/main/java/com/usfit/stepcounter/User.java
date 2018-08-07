package com.usfit.stepcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class User {

    public List<UserInfoPackage> friendsList;

    private static User currentUser;

    public UserInfoPackage myInfo;

    public String username, email, myKey;

    public int topWear, bottomWear, footWear, uAge, totalSteps;

    public boolean isOnline;

    public User(){
        topWear = R.drawable.outfit_t00;
        bottomWear = R.drawable.outfit_b00;
        footWear = R.drawable.outfit_f00;
        friendsList = new ArrayList<>();
        isOnline = false;
    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
        myKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myInfo = new UserInfoPackage(myKey, username);
        totalSteps = 0;
        friendsList = new ArrayList<>();
    }


    public void SetOutfit(int[] outfitArray){
        topWear = outfitArray[0];
        bottomWear = outfitArray[1];
        footWear = outfitArray[2];


    }

    public static void SetCurrentUser(User currentUser){
        User.currentUser = currentUser;
    }

    public static User GetCurrentUser(){
        return User.currentUser;
    }



    public void AddFriend(UserInfoPackage friend){
        friendsList.add(friend);
    }

    public void UpdateUserProfile(User newInfo){

        if(newInfo != null && newInfo.isOnline) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = db.getReference();

            HashMap<String, Object> tempMap = new HashMap<>();
            tempMap.put(newInfo.myKey, newInfo);

            dbRef.child("users").updateChildren(tempMap);
        }
        SetCurrentUser(newInfo);

    }


}
