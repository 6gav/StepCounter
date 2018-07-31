package com.usfit.stepcounter;

import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class User {

    public List<UserInfoPackage> friendsList = new ArrayList<>();

    private static User currentUser;

    public String username, email;

    public int topWear, bottomWear, footWear;

    public User(){

    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
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
}
