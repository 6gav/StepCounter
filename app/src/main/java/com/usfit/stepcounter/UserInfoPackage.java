package com.usfit.stepcounter;

import com.google.firebase.auth.UserInfo;

public class UserInfoPackage {
    public String userID, userName;

    public int mTop, mBot, mFoot;

    public UserInfoPackage() {

    }

    public UserInfoPackage(String userName, String userID){
        this.userID = userID;
        this.userName = userName;
    }

    public void AddCurrentOutfit(){
        User temp = StaticHolderClass.currentUser;
        mTop = temp.mTop;
        mBot = temp.mBot;
        mFoot = temp.mFoot;
    }

    public void AddOutfit(RequestInfo friend) {
        mTop = friend.mTop;
        mBot = friend.mBot;
        mFoot = friend.mFoot;
    }
}
