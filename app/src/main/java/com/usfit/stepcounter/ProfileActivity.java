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

import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfilePhoto;
    Button btnAddFriend;
    ConstraintLayout clytProfileBackground;
    FirebaseStorage storage;

    public void InitProfile(){

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfilePhoto = findViewById(R.id.ivProfileBody);
        btnAddFriend = findViewById(R.id.btnAddFriend);
        clytProfileBackground = findViewById(R.id.clytProfileBackground);

        storage = FirebaseStorage.getInstance();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        ivProfilePhoto.setImageDrawable(mDrawable);


    }
}
