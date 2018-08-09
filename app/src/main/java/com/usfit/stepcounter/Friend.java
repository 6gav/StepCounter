package com.usfit.stepcounter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public class Friend extends UserInfoPackage {

    private ChildEventListener userChange;


    public Friend(){

    }

    public Friend(UserInfoPackage friend){

        mTop = friend.mTop;
        mBot = friend.mBot;
        mFoot = friend.mFoot;
        userName = friend.userName;
        userID = friend.userID;


        userChange = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 UserInfoPackage tempInfo = dataSnapshot.getValue(UserInfoPackage.class);
                 tempInfo.mTop = mTop;
                 tempInfo.mBot = mBot;
                 tempInfo.mFoot = mFoot;
                 tempInfo.userName = userName;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        FirebaseDatabase.getInstance().getReference().child("uids").child(friend.userID).addChildEventListener(userChange);
    }

}
