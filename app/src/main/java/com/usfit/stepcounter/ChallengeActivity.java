package com.usfit.stepcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChallengeActivity extends AppCompatActivity {

    String[] types, challenges;

    ArrayList<String> availableChallenges = new ArrayList<>();

    ArrayList<String> availableChallengesRAW = new ArrayList<>();

    TextView challengePreviewTextView;

    String friendUID;





    Spinner challengeTypes, challengeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        friendUID = getIntent().getStringExtra("mUID");

        challengeTypes = findViewById(R.id.challengeTypeDropDown);
        challengeSpinner = findViewById(R.id.challengeSpinner);

        challengePreviewTextView = findViewById(R.id.challengePreviewTextView);

        challenges = getResources().getStringArray(R.array.challenges);

        types = getResources().getStringArray(R.array.challengeTypes);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);

        challengeTypes.setAdapter(typeAdapter);

        challengeTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ChangeChallenges();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        challengeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UpdatePreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ChangeChallenges();


    }

    private void ChangeChallenges() {
        String selectedType = types[challengeTypes.getSelectedItemPosition()];

        availableChallenges.clear();
        availableChallengesRAW.clear();


        for(int i = 0; i < challenges.length; i++){
            String[] chParts = challenges[i].split(":");
            String newChallenge = "";
            if(chParts[0].equals(selectedType)){

                switch(selectedType){
                    case "Distance":
                        newChallenge = "Complete " + chParts[1] + " Mile(s) for " + chParts[2] + " currency.";
                        break;
                    case "Calories":
                        newChallenge = "Burn " + chParts[1] + " Calories for " + chParts[2] + " currency.";
                        break;
                }

                if(!newChallenge.equals("")) {
                    availableChallengesRAW.add(challenges[i]);
                    availableChallenges.add(newChallenge);
                }
            }
        }

        ArrayAdapter<String> challengeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, availableChallenges);
        challengeSpinner.setAdapter(challengeAdapter);

        UpdatePreview();
    }

    private void UpdatePreview() {


        User tempUser = StaticHolderClass.currentUser;

        String newPreview = "\"" + tempUser.mUsername + " challenges you to " + challengeSpinner.getSelectedItem().toString() + "\"";

        challengePreviewTextView.setText(newPreview);

    }

    public void SendChallenge(View v){
        UserInfoPackage currentUser = new UserInfoPackage(StaticHolderClass.currentUser.mUsername, StaticHolderClass.currentUser.mUID);

        String[] currentChallenge = availableChallengesRAW.get(challengeSpinner.getSelectedItemPosition()).split(":");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        String cKey = ref.push().getKey();

        Challenge newChallenge = new Challenge(cKey, currentChallenge[0], currentChallenge[1], currentChallenge[2], currentChallenge[3], currentUser);


        Map<String, Object> childMap = new HashMap<>();

        childMap.put(cKey, newChallenge);

        ref.child("challenge-invites").child(friendUID).updateChildren(childMap);

        Toast.makeText(this, "Challenge sent to friend.", Toast.LENGTH_SHORT).show();

    }
}
