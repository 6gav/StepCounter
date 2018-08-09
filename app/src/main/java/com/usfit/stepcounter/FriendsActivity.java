package com.usfit.stepcounter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendsActivity extends AppCompatActivity {

    //Components
    EditText usernameInput;
    TextView friendName;
    Button searchButton, sendButton;
    View avatarLayout;

    //Variables
    User displayedUser;

    //Firebase
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();


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

        //Variables


        ImageView temp = avatarLayout.findViewById(R.id.ivAvatarBody);
        temp.setImageDrawable(getDrawable(R.drawable.bck_lavender));



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
                    db.child("users").child(tempInfo.userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            displayedUser = dataSnapshot.getValue(User.class);
                            LoadOutfit();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void LoadOutfit(){

    }
}
