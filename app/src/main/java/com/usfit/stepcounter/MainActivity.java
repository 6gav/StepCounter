package com.usfit.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private TextView count, count2, count3;
    private int StepCounts, TapCount, MonValue, StepsUntilMon = 10;
    boolean activityRunning, DebugMode, FirstRun;
    private Button debugStepButton;
    DetailManager detailManager;
    String userName;



    private SharedPreferences MoneyPref;
    private SharedPreferences.Editor MoneyEditor;
    private SharedPreferences AvatarPref;
    private TextView MoneyCounterTextView;



    //Firebase
    DatabaseReference db;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser fUser;


    Handler h = new Handler();
    int delay = 250; //1 second=1000 milliseconds, 15*1000=15seconds
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.SharedStepData), MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstRun = true;

        //Firebase

        db = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();

        if(fUser == null) {
            LoginStart();
        }



        detailManager = new DetailManager(this);
        TapCount = 4;
        DebugMode = false;

        count = findViewById(R.id.countTextView);

        MoneyPref = getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);
        //AvatarPref = getApplicationContext().getSharedPreferences("com.usfit.stepcounter.marketplace", Context.MODE_PRIVATE);
        MonValue = MoneyPref.getInt("MonValue", 0);
        MoneyEditor = MoneyPref.edit();
        MoneyCounterTextView = findViewById(R.id.tvMonValueInfo);
        //((TextView)findViewById(R.id.tvGoal1)).setText(sharedPreferences.getString("AllGoals",""));

        h.postDelayed(new Runnable() {
            public void run() {
                AchievementChecker.Check(getApplicationContext());
                runnable = this;
                h.postDelayed(runnable, delay);
            }
        }, delay);

        DetailManager.DrawPlayer(this,MoneyPref);
    }


    @Override
    protected void onDestroy() {
        detailManager.Release();
        super.onDestroy();

        StaticHolderClass.currentUser.FullUpdateUser(this);


    }



    private void DebugEnable() {
        debugStepButton.setVisibility(View.VISIBLE);
        debugStepButton.setClickable(true);
        debugStepButton.setEnabled(true);
    }


    public void MoneyUpdate(View v) {
        TextView MoneyText = findViewById(R.id.tvMonValueInfo);
        String tempMoneytext = "X " + MoneyPref.getInt("MonValue", 0);
        MoneyText.setText(tempMoneytext);
    }

    public void DebugClicker(View v) {
        if (TapCount > 0) {


            if (TapCount <= 4) {
                String tapText = "You are " + TapCount + " taps away from debug mode.";
                Toast.makeText(this, tapText, Toast.LENGTH_SHORT).show();
            }
            TapCount--;
        } else if (TapCount == 0) {
            DebugMode = true;
            DebugEnable();
            Toast.makeText(this, "You are now in debug mode.", Toast.LENGTH_SHORT).show();
            TapCount--;
        }

    }


    protected void onResume() {
        super.onResume();
        activityRunning = true;

        String temp = "X " + MoneyPref.getInt("MonValue", 0);
        MoneyCounterTextView.setText(temp);
        DetailManager.DrawPlayer(this,MoneyPref);
        fUser = mAuth.getCurrentUser();
        LoadUser();

        if(!FirstRun)
        CreateNewUser();
        else
            FirstRun = false;

    }

    public void CreateNewUser() {
        FirebaseUser tempFUser = FirebaseAuth.getInstance().getCurrentUser();
        if (StaticHolderClass.currentUser == null && tempFUser != null) {
            Intent n = new Intent(this, NewUserActivity.class);
            startActivity(n);
        }
    }

    public void onPause() {
        super.onPause();
        activityRunning = false;
    }


    public void sendMessage(View v) {
        detailManager.PlaySound(R.raw.sfx_confirm);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnALogger),getResources().getDrawable(R.drawable.blip_blue));

        Intent newActivity = new Intent(this, MainMenuActivity.class);
        startActivity(newActivity);


    }


    public void ToAchievements(View v) {
        detailManager.PlaySound(R.raw.sfx_confirm);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnAchievements),getResources().getDrawable(R.drawable.blip_blue));

        Intent n = new Intent(this, AchievementsActivity.class);
        startActivity(n);
    }
/*
    allows to change the avatar's skin, hair, etc.
    public void ToAvatar(View v) {
        //PlayAnimation(btnAchievements,getResources().getDrawable(R.drawable.blip_blue));
        Intent n = new Intent(this, AvatarActivity.class);
        startActivity(n);
    }
*/

public  void ToProfile(View V){
    detailManager.PlaySound(R.raw.sfx_confirm);
    detailManager.PlayAnimation((Button)findViewById(R.id.btnShop),getResources().getDrawable(R.drawable.blip_blue));
    Intent n = new Intent(this, ProfileActivity.class);
    startActivity(n);
}
    public void ToShop(View v) {
        detailManager.PlaySound(R.raw.sfx_confirm);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnShop),getResources().getDrawable(R.drawable.blip_blue));
        Intent n = new Intent(this, ShopActivity.class);
        startActivity(n);
    }

    public void ToGoals(View v) {
        detailManager.PlaySound(R.raw.sfx_confirm);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnGoals),getResources().getDrawable(R.drawable.blip_blue));
        Intent n = new Intent(this, GoalsActivity.class);
        startActivity(n);
    }

    public void ToInventory(View v) {
        detailManager.PlaySound(R.raw.sfx_confirm);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnInventory),getResources().getDrawable(R.drawable.blip_blue));
        Intent n = new Intent(this, Inventory.class);
        startActivity(n);
    }

    public void ToPreferences(View v){
        detailManager.PlaySound(R.raw.sfx_confirm);
        detailManager.PlayAnimation((Button)findViewById(R.id.btnUPrefs),getResources().getDrawable(R.drawable.blip_blue));
        Intent n = new Intent(this, UserPreferences.class);
        startActivity(n);
    }

    public void ToLogin(View v){
        Intent n = new Intent(this,LoginActivity.class);
        startActivity(n);
    }

    public void ToFriends(View v){
        if(fUser != null && StaticHolderClass.currentUser != null) {
            Intent n = new Intent(this, FriendsActivity.class);
            startActivity(n);
        }
        else if(StaticHolderClass.currentUser == null && fUser != null){
            Toast.makeText(this, "You must create a username before proceeding.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, NewUserActivity.class));
        }
        else
        {
            Toast.makeText(this, "You need to be logged in to do that.", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoginStart(){
        final int RC_SIGN_IN = 123;

// ...

// Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }
    public void LoginStart(View v){
        LoginStart();
    }


    public void LoadUser(){
    if(fUser != null) {
        db.child("users").child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User tempUser = dataSnapshot.getValue(User.class);
                StaticHolderClass.currentUser = tempUser;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    else
        Toast.makeText(this, "Many features will be disabled if you do not log in.", Toast.LENGTH_LONG).show();


    }

    public void SignOut(View v){
        mAuth.signOut();
        fUser = null;
        StaticHolderClass.currentUser = null;

    }


}
