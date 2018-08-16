package com.usfit.stepcounter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity {

    Button btnProfileFriend,btnProfilePrefs;
    ConstraintLayout clytProfileBackground;

    ImageView aTop, aBot, aFoot;

    User displayedUser;

    String mUsername;

    TextView tvFriendCount,tvFriendTitle,tvStepCount,tvStepTitle,tvProfileName;

    Boolean mCurrentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mCurrentUser = getIntent().getBooleanExtra("mCurrentUser", false);

        btnProfileFriend = findViewById(R.id.btnProfileFriend);
        btnProfilePrefs = findViewById(R.id.btnProfileUPrefs);
        clytProfileBackground = findViewById(R.id.clytProfileBackground);

        tvFriendCount = findViewById(R.id.tvFriendCount);
        tvFriendTitle = findViewById(R.id.tvFriendTitle);
        tvStepCount = findViewById(R.id.tvStepsCount);
        tvStepTitle = findViewById(R.id.tvStepsTitle);
        tvProfileName = findViewById(R.id.tvProfileName);

        aTop = clytProfileBackground.findViewById(R.id.ivAvatarTop);
        aBot = clytProfileBackground.findViewById(R.id.ivAvatarBottom);
        aFoot = clytProfileBackground.findViewById(R.id.ivAvatarFeet);
        String setupdata;

        setupdata = getIntent().getStringExtra("DisplayType");
        if(setupdata != null) {
            switch (setupdata) {
                case "FriendSearch":
                    btnProfilePrefs.setText("Add Friend");
                    btnProfilePrefs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ///TODO: Add Friend
                            finish();
                        }
                    });
                    break;
                case "FriendView":
                    btnProfilePrefs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent n = new Intent(getApplicationContext(), ChallengeActivity.class);
                            n.putExtra("mUID", StaticHolderClass.displayedUser.mUID);
                            startActivity(n);
                        }
                    });
                    btnProfilePrefs.setText("Send Challenge");



                    break;
                case"MyProfile":
                    btnProfilePrefs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToUserPrefs(v);
                        }
                    });
                    break;

                default:

                    break;
            }
        }


            InitUser();



    }


    public void InitUser(){


        InitProfile(StaticHolderClass.displayedUser);
        DetailManager.DrawPlayer(this, StaticHolderClass.displayedUser);

    }

    public void ToFriends(View v){
        Intent n = new Intent(this, FriendsListActivity.class);
        n.putExtra("mCurrentUser", false);
        startActivity(n);

    }

    public void ToUserPrefs(View v){
        Intent n = new Intent(this,UserPreferences.class);
        startActivity(n);

    }

    public void InitProfile(User user){
        if(user == null)return;
        tvProfileName.setText(user.mUsername);
        tvStepCount.setText(String.valueOf(user.mTotalSteps));
        tvFriendCount.setText(String.valueOf(user.friendsList.size()));
    };
}
