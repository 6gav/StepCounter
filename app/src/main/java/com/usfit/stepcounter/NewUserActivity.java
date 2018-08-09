package com.usfit.stepcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewUserActivity extends AppCompatActivity {


    //Components
    EditText usernameInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        usernameInput = findViewById(R.id.usernameNewUserInput);

        if(StaticHolderClass.currentUser != null){
            finish();
        }
    }

    public void SaveUser(View v){
        String username = usernameInput.getText().toString();
        if(!username.equals(""))
        {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            User tempUser = new User(username, currentUser.getEmail(), currentUser.getUid());
            tempUser.LoadOutfit(this);

            StaticHolderClass.currentUser = tempUser;

            tempUser.PublishUser();

        }
    }
}
