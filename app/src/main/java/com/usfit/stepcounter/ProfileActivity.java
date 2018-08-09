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

    Button btnProfileFriend,btnProfilePrefs;
    ConstraintLayout clytProfileBackground;

    TextView tvFriendCount,tvFriendTitle,tvStepCount,tvStepTitle,tvProfileName;


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

        if(StaticHolderClass.currentUser != null){
            InitProfile(StaticHolderClass.currentUser);
            btnProfileFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent n;
                    n = new Intent();

                }
            });
        }
    }

    public void InitProfile(User user){
        if(user == null)return;
        tvProfileName.setText(user.mUsername);
        tvStepCount.setText(String.valueOf(user.mTotalSteps));
        tvFriendCount.setText(String.valueOf(user.friendsList.size()));
    };
}
