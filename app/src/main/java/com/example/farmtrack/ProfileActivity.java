package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.farmtrack.util.JournalApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private TextView username;
    private TextView userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        userEmail = findViewById(R.id.profile_userEmail);
        username = findViewById(R.id.profile_username);

        username.setText(JournalApi.getInstance().getUsername());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
//                Take user to their journal
                if (user != null && firebaseAuth != null){
                    startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
//                    finish();
                }
                break;
            case R.id.action_home:
//                Take user to their journal
                startActivity(new Intent(ProfileActivity.this, HomePageActivity.class));
//                if (user != null && firebaseAuth != null){
//                    startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
////                    finish();
//                }
                break;
            case R.id.action_signout:
//                make user sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}