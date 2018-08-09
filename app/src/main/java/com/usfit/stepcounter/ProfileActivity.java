package com.usfit.stepcounter;

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

    Button btnProfileFriend;
    ConstraintLayout clytProfileBackground;

    ImageView aTop, aBot, aFoot;

    User displayedUser;

    String mUsername;

    TextView tvFriendCount,tvFriendTitle,tvStepCount,tvStepTitle,tvProfileName;

    public void InitProfile(User user){
        tvProfileName.setText(user.mUsername);
        tvStepCount.setText(String.valueOf(user.mTotalSteps));
        //tvStepCount.setText(user.friendsList.size());
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnProfileFriend = findViewById(R.id.btnProfileFriend);
        clytProfileBackground = findViewById(R.id.clytProfileBackground);

        tvFriendCount = findViewById(R.id.tvFriendCount);
        tvFriendTitle = findViewById(R.id.tvFriendTitle);
        tvStepCount = findViewById(R.id.tvStepsCount);
        tvStepTitle = findViewById(R.id.tvStepsTitle);
        tvProfileName = findViewById(R.id.tvProfileName);

        aTop = clytProfileBackground.findViewById(R.id.ivAvatarTop);
        aBot = clytProfileBackground.findViewById(R.id.ivAvatarBottom);
        aFoot = clytProfileBackground.findViewById(R.id.ivAvatarFeet);

        if(StaticHolderClass.displayedUser != null)
        displayedUser = StaticHolderClass.displayedUser;



        InitUser();


    }


    public void InitUser(){
        tvProfileName.setText(displayedUser.mUsername);
        tvStepCount.setText(String.valueOf(displayedUser.mTotalSteps));

        aTop.setImageDrawable(getDrawable(R.drawable.outfit_t00 + displayedUser.mTop));
        aBot.setImageDrawable(getDrawable(R.drawable.outfit_b00 + displayedUser.mBot));
        aFoot.setImageDrawable(getDrawable(R.drawable.outfit_f00 + displayedUser.mFoot));
    }

    public void ToFriends(View v){

        Intent n = new Intent(this, FriendsListActivity.class);
        n.putExtra("mCurrentUser", false);
        startActivity(n);

    }
}
