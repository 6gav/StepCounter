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

    private List<UserInfoPackage> friends = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        InitNames();



    }

    private void InitNames(){
        for(int i = 0; i < friends.size(); i++){
            mNames.add(friends.get(i).userName);
            mUIDS.add(friends.get(i).userID);
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
