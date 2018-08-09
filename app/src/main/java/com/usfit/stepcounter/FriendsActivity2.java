/*
package com.usfit.stepcounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsActivity2 extends AppCompatActivity {

    //Testing
    Map<String, String> testMap = new HashMap<>();

    //Preferences
    SharedPreferences outfitPrefs;

    //Variables
    String currentUID, friendName, friendUID, myUsername, sendUsername;
    UserInfoPackage myInfo, sendInfo;
    List<FriendRequestHolder> requestHolderList = new ArrayList<>();
    FriendRequestHolder currentRequest;

    //Components
    EditText currentUsername, currentAge;
    TextView currentRequestTextView;

    ImageView playerTop, playerBot, playerFoot;

    //Users
    User currentUser, displayedUser, tempFriendUser;
    FirebaseUser LoggedUser;

    List<UserInfoPackage> tempList;
    ChildEventListener requestChildListener, acceptChildListener;
    ValueEventListener outfitEventListener;




    FirebaseAuth mAuth;
    //FirebaseUser currentUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    DatabaseReference usersRef, uidsRef, requestsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends2);

        mAuth = FirebaseAuth.getInstance();
        LoggedUser = mAuth.getCurrentUser();
        currentUID = mAuth.getCurrentUser().getUid();

        currentUser = User.GetCurrentUser();

        usersRef = ref.child("users");
        uidsRef = ref.child("uids");
        requestsRef = ref.child("friend-requests").child(currentUID);






        //Components
        currentUsername = findViewById(R.id.myUserPlainText);

        playerTop = findViewById(R.id.playerShirtImage);

        playerBot = findViewById(R.id.playerBottomImage);

        playerFoot = findViewById(R.id.playerFootImage);

        currentRequestTextView = findViewById(R.id.currentRequestTextView);

        currentAge = findViewById(R.id.userAgeInput);


        //Preferences
        outfitPrefs = getApplicationContext().getSharedPreferences("com.usfit.stepcounter.marketplace",MODE_PRIVATE);


        AddOutfitListener();
        LoadOutfit();
        ListenForRequests();
        ListenForAccepts();

    }

    public void AddUser(View v){
        if(currentUser == null){
            currentUser = new User(currentUsername.getText().toString(), LoggedUser.getEmail());
            currentUser.uAge = Integer.valueOf(currentAge.getText().toString());
        }
        SaveOutfit();
        SaveUser();

    }

    public void SaveOutfit(){
        int[] tempOutfit = new int[3];

        tempOutfit[0] = outfitPrefs.getInt("A_TOP", R.drawable.outfit_t00);
        tempOutfit[1] = outfitPrefs.getInt("A_BOT", R.drawable.outfit_b00);
        tempOutfit[2] = outfitPrefs.getInt("A_FOT", R.drawable.outfit_f00);
        currentUser.SetOutfit(tempOutfit);
    }

    public void FindUser(View v){
        EditText findUser = findViewById(R.id.otherUserPlainText);
        sendUsername = findUser.getText().toString();

        uidsRef.child(sendUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sendInfo = dataSnapshot.getValue(UserInfoPackage.class);
                SendRequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("WhyAreYouLikeThis...", "onCancelled: Failed");
            }
        });





    }

    public void SendRequest(){
        if(sendInfo != null) {
            friendUID = sendInfo.userID;

            String key = usersRef.push().getKey();
            myUsername = currentUser.username;
            FriendRequestHolder fReq = new FriendRequestHolder(myUsername, currentUID, key);
            Map<String, Object> childMap = new HashMap<>();

            childMap.put("/friend-requests/" + friendUID + "/" + key, fReq);

            ref.updateChildren(childMap);
        }
    }



    public void SaveUser(){

        usersRef.child(currentUID).setValue(currentUser);

        myInfo = new UserInfoPackage(currentUID, currentUser.username);
        uidsRef.child(currentUser.username).setValue(myInfo);

        currentUser.isOnline = true;
        User.SetCurrentUser(currentUser);

    }



    public void SignOut(View v){
        FirebaseAuth.getInstance().signOut();
    }

    private void AddOutfitListener(){
        outfitEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayedUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void LoadOutfit(){
        if(currentRequest != null) {
            usersRef.child(currentRequest.senderID).addValueEventListener(outfitEventListener);
            playerFoot.setImageDrawable(getResources().getDrawable(displayedUser.footWear));
            playerTop.setImageDrawable(getResources().getDrawable(displayedUser.topWear));
            playerBot.setImageDrawable(getResources().getDrawable(displayedUser.bottomWear));
        }
        else {
            displayedUser = new User();
            playerFoot.setImageDrawable(getResources().getDrawable(displayedUser.footWear));
            playerTop.setImageDrawable(getResources().getDrawable(displayedUser.topWear));
            playerBot.setImageDrawable(getResources().getDrawable(displayedUser.bottomWear));
        }
    }

    public void ListenForRequests(){

        requestChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendRequestHolder friendRequestHolder = dataSnapshot.getValue(FriendRequestHolder.class);
                requestHolderList.add(friendRequestHolder);
                LoadRequests();
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
        requestsRef.addChildEventListener(requestChildListener);
    }

    public void ListenForAccepts(){
        acceptChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserInfoPackage acceptedFriend = dataSnapshot.getValue(UserInfoPackage.class);
                ref.child("accept-notices").child(currentUser.myKey).child(acceptedFriend.aKey).setValue(null);
                acceptedFriend.SetAKey("");
                currentUser.AddFriend(acceptedFriend);
                currentUser.UpdateUserProfile(currentUser);
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
        ref.child("accept-notices").child(currentUID).addChildEventListener(acceptChildListener);
    }


    public void AcceptRequest(View v){
        if(currentRequest != null) {

            //Logic for current user
            requestsRef.child(currentRequest.key).setValue(null);
            currentUser.AddFriend(new UserInfoPackage(currentRequest.senderID, currentRequest.sender));
            currentUser.UpdateUserProfile(currentUser);


            //Logic for friend






            String myUID = currentUser.myKey;

            String key = usersRef.push().getKey();
            myUsername = currentUser.username;
            UserInfoPackage newAcceptInfo = new UserInfoPackage(myUID, myUsername);
            newAcceptInfo.SetAKey(key);
            Map<String, Object> childMap = new HashMap<>();

            childMap.put("/accept-notices/" + currentRequest.senderID + "/" + key, newAcceptInfo);

            ref.updateChildren(childMap);





            currentRequest = null;
            requestHolderList.clear();;

            LoadRequests();
        }
        else
            Toast.makeText(this, "You have no friend request at the moment.", Toast.LENGTH_SHORT).show();

    }

    public void LoadRequests() {
        if (!requestHolderList.isEmpty()) {
            currentRequest = requestHolderList.get(0);
            currentRequestTextView.setText(currentRequest.sender);
        } else {
            currentRequestTextView.setText(R.string.NoRequestCurrently);
            requestHolderList.clear();
        }
        LoadOutfit();
    }

    public void ToFriendsList(View v){
        if(currentUser != null && currentUser.friendsList != null && currentUser.friendsList.size() != 0) {
            Intent n = new Intent(this, FriendsListActivity.class);
            startActivity(n);
        }
        else
        {
            Toast.makeText(this, "You have no friends currently, or have not yet signed up.", Toast.LENGTH_SHORT).show();
        }
    }

}


*/
