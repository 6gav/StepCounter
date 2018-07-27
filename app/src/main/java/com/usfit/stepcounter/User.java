package com.usfit.stepcounter;

public class User {

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


}
