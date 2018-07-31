package com.usfit.stepcounter;

import java.util.List;
import java.util.Vector;

public class User {

    private static User currentUser;

    public String username;

    public int topWear, bottomWear, footWear;


    public User(){

    }

    public User(String username){
        this.username = username;
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
}
