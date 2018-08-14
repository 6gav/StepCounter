package com.usfit.stepcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity {


    //Variables
    private ArrayList<String> mUIDS = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();

    Boolean mCurrentUser;

    private List<Friend> friends = new ArrayList<>();
    private List<Friend> otherfr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        mCurrentUser = getIntent().getBooleanExtra("mCurrentUser", true);

        friends = StaticHolderClass.currentUser.friendsList;
        if(StaticHolderClass.displayedUser == null){
            otherfr = StaticHolderClass.currentUser.friendsList;
        }
        else
        otherfr = StaticHolderClass.displayedUser.friendsList;

        if(mCurrentUser)
        InitNames(friends);
        else
            InitNames(otherfr);



    }

    private void InitNames(List<Friend> nFriends){

        for(int i = 0; i < nFriends.size(); i++){
            mNames.add(nFriends.get(i).userName);
            mUIDS.add(nFriends.get(i).userID);
        }
        InitView();
    }

    private void InitView(){
        RecyclerView recyclerView = findViewById(R.id.friendsRecyclerView);
        FriendListRecycleAdapter adapter = new FriendListRecycleAdapter(mNames, mUIDS, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
