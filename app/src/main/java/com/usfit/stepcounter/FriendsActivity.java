package com.usfit.stepcounter;

import android.app.DownloadManager;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static com.usfit.stepcounter.StaticHolderClass.currentUser;

public class FriendsActivity extends AppCompatActivity {

    //Components
    EditText usernameInput;
    TextView friendName, requestUserNameTextView;
    Button searchButton, sendButton;
    View avatarLayout;
    ImageView avatarHead, avatarTop, avatarBot, avatarFoot;

    //Variables
    UserInfoPackage displayedUser;
    RequestInfo tempRequest;

    //Firebase
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    ChildEventListener requestListener, acceptListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        //Components
        usernameInput = findViewById(R.id.findFriendNameInput);

        friendName = findViewById(R.id.friendNameTextView);

        searchButton = findViewById(R.id.findFriendButton);

        sendButton = findViewById(R.id.sendFriendRequestButton);

        avatarLayout = findViewById(R.id.findFriendAvatarLayout);

        avatarHead = avatarLayout.findViewById(R.id.ivAvatarHead);

        avatarBot = avatarLayout.findViewById(R.id.ivAvatarBottom);

        avatarTop = avatarLayout.findViewById(R.id.ivAvatarTop);

        avatarFoot = avatarLayout.findViewById(R.id.ivAvatarFeet);

        requestUserNameTextView = findViewById(R.id.friendRequestTempName);


        //Variables



        AddListeners();



        sendButton.setEnabled(false);


    }


    public void Search(View v){
        String searchName = usernameInput.getText().toString();

        if(!searchName.equals("")){
            AddSearchListener(searchName);
        }
    }

    private void AddSearchListener(String searchName){
        db.child("uids").child(searchName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfoPackage tempInfo = dataSnapshot.getValue(UserInfoPackage.class);
                if(tempInfo == null){
                    Toast.makeText(getApplicationContext(), "This user doesn't exist", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayedUser = dataSnapshot.getValue(UserInfoPackage.class);
                    LoadOutfit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.child("friend-requests").child(currentUser.mUID).removeEventListener(requestListener);
        db.child("accept-requests").child(currentUser.mUID).removeEventListener(acceptListener);



    }

    private void LoadOutfit(){

        avatarTop.setImageDrawable(getDrawable(R.drawable.outfit_t00 + displayedUser.mTop));
        avatarBot.setImageDrawable(getDrawable(R.drawable.outfit_b00 + displayedUser.mBot));
        avatarFoot.setImageDrawable(getDrawable(R.drawable.outfit_f00 + displayedUser.mFoot));

        sendButton.setVisibility(View.VISIBLE);
        sendButton.setEnabled(true);

    }

    public void SendRequest(View v){
        DatabaseReference requestsRef = db.child("friend-requests");
        String RKey = requestsRef.child(displayedUser.userID).push().getKey();

        User currentUser = StaticHolderClass.currentUser;
        RequestInfo sentRequest = new RequestInfo(currentUser.mUID, currentUser.mUsername, RKey);
        sentRequest.AddCurrentOutfit();

        Map<String, Object> childMap = new HashMap<>();
        childMap.put("/" + displayedUser.userID + "/" + RKey, sentRequest);

        requestsRef.updateChildren(childMap);


    }


    private void AddListeners(){

        final User currentUser = StaticHolderClass.currentUser;

        //New Friend Requests
        requestListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                StaticHolderClass.currentUser.AddRequest(dataSnapshot.getValue(RequestInfo.class));
                tempRequest = dataSnapshot.getValue(RequestInfo.class);
                requestUserNameTextView.setText(dataSnapshot.getValue(RequestInfo.class).userName);

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

        //Accepted Friends
        acceptListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                StaticHolderClass.currentUser.AddFriend(dataSnapshot.getValue(RequestInfo.class));
                StaticHolderClass.currentUser.FullUpdateUser(getApplicationContext());
                db.child("accept-requests").child(currentUser.mUID).child(dataSnapshot.getValue(RequestInfo.class).RKey).setValue(null);

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

        db.child("friend-requests").child(currentUser.mUID).addChildEventListener(requestListener);
        db.child("accept-requests").child(currentUser.mUID).addChildEventListener(acceptListener);

    }

    public void ToFriendsList(View v){
        startActivity(new Intent(this, FriendsListActivity.class));
    }


    public void AcceptRequest(View v){

        if(tempRequest == null) {
            Toast.makeText(this, "You don't have a friend request currently", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference acceptsRef = db.child("accept-requests");

        User currentUser = StaticHolderClass.currentUser;

        StaticHolderClass.currentUser.AddFriend(tempRequest);

        Map<String, Object> childMap = new HashMap<>();

        String RKey = acceptsRef.child(tempRequest.userID).push().getKey();

        RequestInfo newAccept = new RequestInfo(currentUser.mUID, currentUser.mUsername, RKey);

        childMap.put("/" + tempRequest.userID + "/" + RKey, newAccept);

        acceptsRef.updateChildren(childMap);

        db.child("friend-requests").child(currentUser.mUID).child(tempRequest.RKey).setValue(null);

        tempRequest = null;

        requestUserNameTextView.setText("No Request.");


    }


}
