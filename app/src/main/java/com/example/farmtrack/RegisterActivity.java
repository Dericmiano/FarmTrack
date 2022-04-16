package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farmtrack.util.JournalApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = db.collection("Users");




    private ConstraintLayout createAccountButton;
    private EditText usernameEditTEXT;
    private ProgressBar progressBar;
    private EditText emailEditText;
    private  EditText passwordText;
    private EditText confirmPassword;
    private ConstraintLayout signInBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        createAccountButton = findViewById(R.id.RegisterButton);
        usernameEditTEXT = findViewById(R.id.register_username);
        emailEditText = findViewById(R.id.register_email);
        passwordText = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_confirm_password);
        progressBar = findViewById(R.id.register_progressBar);
        signInBtn = findViewById(R.id.register_signIn_button);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null){
                //user is alreadty logged in

            }else {
                //no user yet

            }
        };
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailEditText.getText().toString())&&
                        !TextUtils.isEmpty(passwordText.getText().toString()) &&
                        !TextUtils.isEmpty(usernameEditTEXT.getText().toString())){
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordText.getText().toString().trim();
                    String username = usernameEditTEXT.getText().toString().trim();
                    createUserEmailAccount(email,password, username);

                }else {
                    Toast.makeText(RegisterActivity.this, "empty fields not allowed"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void createUserEmailAccount(String email, String password ,final String username) {
        if (!TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(username)){
            progressBar.setVisibility(View.VISIBLE);
            Log.d("TAG", "createUserEmailAccount: still loading at this point ");
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //we take user to add journal activity
                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                String currentUserId=currentUser.getUid();
                                //create a user map so we can create a user in collection
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username",username);
                                //save to fire store database
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()){
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    String name = task.getResult()
                                                                            .getString("username");
                                                                    JournalApi journalApi = JournalApi.getInstance();
                                                                    journalApi.setUserId(currentUserId);
                                                                    journalApi.setUsername(name);

                                                                    Intent intent = new Intent(RegisterActivity.this,
                                                                            LoginActivity.class);
                                                                    intent.putExtra("username",name);
                                                                    intent.putExtra("userId", currentUserId);
                                                                    startActivity(intent);
                                                                }else {
                                                                    progressBar.setVisibility(View.INVISIBLE);

                                                                }

                                                            }
                                                        });

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            }else {
                                //something went wrong
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }else {

        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}