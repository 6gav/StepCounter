package com.usfit.stepcounter;

public class StaticHolderClass {

    public static User currentUser, displayedUser;

    public static int mDisplayTop, mDisplayBot, mDisplayFoot;

    public static User GetUserFromPackage(UserInfoPackage infoPackage){
        User user = new User();
        user.mTop =infoPackage.mTop;
        user.mBot = infoPackage.mBot;
        user.mFoot= infoPackage.mFoot;
        user.mface= infoPackage.mFace;
        user.mhair= infoPackage.mHair;
        user.mbody= infoPackage.mBody;
        return user;

    }
}
