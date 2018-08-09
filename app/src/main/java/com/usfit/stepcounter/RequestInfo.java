package com.usfit.stepcounter;

public class RequestInfo extends UserInfoPackage {

    public String RKey;

    public RequestInfo(){

    }

    public RequestInfo(String userID, String userName, String RKey){
        this.userID = userID;
        this.userName = userName;
        this.RKey = RKey;
    }
}
