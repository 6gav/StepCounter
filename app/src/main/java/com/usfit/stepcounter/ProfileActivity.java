package com.usfit.stepcounter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity {

    Button btnProfileFriend;
    ConstraintLayout clytProfileBackground;

    TextView tvFriendCount,tvFriendTitle,tvStepCount,tvStepTitle,tvProfileName;
    public void InitProfile(User user){
        tvProfileName.setText(user.username);
        tvStepCount.setText(user.totalSteps);
        tvStepCount.setText(user.friendsList.size());
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

        if(User.GetCurrentUser() != null){
            InitProfile(User.GetCurrentUser());
        }
    }
}
