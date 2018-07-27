package com.usfit.stepcounter;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FriendsActivity extends AppCompatActivity {


    //Preferences
    SharedPreferences outfitPrefs;


    //Components
    EditText currentUsername;

    ImageView playerTop, playerBot, playerFoot;

    //Users
    User currentUser, displayedUser;
    FirebaseUser LoggedUser;




    FirebaseAuth mAuth;
    //FirebaseUser currentUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    DatabaseReference usersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friends);

        usersRef = ref.child("users");

        mAuth = FirebaseAuth.getInstance();





        //Components
        currentUsername = findViewById(R.id.myUserPlainText);

        playerTop = findViewById(R.id.playerShirtImage);

        playerBot = findViewById(R.id.playerBottomImage);

        playerFoot = findViewById(R.id.playerFootImage);

        //Preferences
        outfitPrefs = getApplicationContext().getSharedPreferences("com.usfit.stepcounter.marketplace",MODE_PRIVATE);



    }

    public void AddUser(View v){

        String userKey = currentUsername.getText().toString();

        currentUser = new User(userKey);
    }

    public void FindUser(View v){
        EditText findUser = findViewById(R.id.otherUserPlainText);
        String friendName = findUser.getText().toString();
        usersRef.child(friendName).addValueEventListener(new ValueEventListener() {
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


    public void SaveOutfit(View v){
        int[] tempOutfit = new int[3];

        tempOutfit[0] = outfitPrefs.getInt("A_TOP", R.drawable.outfit_t00);
        tempOutfit[1] = outfitPrefs.getInt("A_BOT", R.drawable.outfit_b00);
        tempOutfit[2] = outfitPrefs.getInt("A_FOT", R.drawable.outfit_f00);
        currentUser.SetOutfit(tempOutfit);
    }

    public void SaveUser(View v){
        usersRef.child(currentUser.username).setValue(currentUser);
    }



    public void SignOut(View v){
        FirebaseAuth.getInstance().signOut();
    }

    private void LoadOutfit(){
        playerFoot.setImageDrawable(getResources().getDrawable(displayedUser.footWear));
        playerTop.setImageDrawable(getResources().getDrawable(displayedUser.topWear));
        playerBot.setImageDrawable(getResources().getDrawable(displayedUser.bottomWear));
    }

}


