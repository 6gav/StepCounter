package com.usfit.stepcounter;

import com.google.firebase.auth.UserInfo;

public class UserInfoPackage {
    public String userID, userName;

    public UserInfoPackage(){

    }

    public UserInfoPackage(String userID, String userName){
        this.userID = userID;
        this.userName = userName;
    }
}
