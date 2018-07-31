package com.usfit.stepcounter;

import java.util.HashMap;
import java.util.Map;

public class FriendRequestHolder {
    public String sender, senderID, key;
    public FriendRequestHolder(String Sent, String ID, String key){
        sender = Sent;
        senderID = ID;
        this.key = key;
    }

    public FriendRequestHolder(){

    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("sender", sender);
        result.put("senderID", senderID);
        return result;
    }
}
