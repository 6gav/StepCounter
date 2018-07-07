package com.stepcountercounter.softdev.stepcounter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfilePhoto;
    Button btnAddFriend;
    ConstraintLayout clytProfileBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        btnAddFriend = findViewById(R.id.btnAddFriend);
        clytProfileBackground = findViewById(R.id.clytProfileBackground);
        
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.psychia);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        ivProfilePhoto.setImageDrawable(mDrawable);
    }
}
