package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.farmtrack.model.Journal;
import com.example.farmtrack.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_CODE =1 ;
    private Button saveButton;
    private ProgressBar progressBar;
    private ImageView addPhotoButton;
    private EditText thoughtsEditText;
    private EditText titleEditText;
    private TextView currentUserTextView;

    private ImageView imageView;

    private String currentUserId;
    private String currentUsername;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    // connection to firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Journal");
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        titleEditText  = findViewById(R.id.postTitleEt);
        thoughtsEditText = findViewById(R.id.postDescriptioEt);
        currentUserTextView = findViewById(R.id.postusernameTextView);

        imageView = findViewById(R.id.appendPostimageView);

        saveButton = findViewById(R.id.postSaveJournalbutton);
        saveButton.setOnClickListener(this);
        addPhotoButton = findViewById(R.id.postCameraButton);
        addPhotoButton.setOnClickListener(this);

        progressBar.setVisibility(View.INVISIBLE);


        if (JournalApi.getInstance() != null){
            currentUserId = JournalApi.getInstance().getUserId();
            currentUsername = JournalApi.getInstance().getUsername();

            currentUserTextView.setText(currentUsername);

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user = firebaseAuth.getCurrentUser();
                    if (user != null){

                    }else {

                    }
                }
            };
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.postSaveJournalbutton:
                //save journal
                saveJournal();
                break;
            case R.id.postCameraButton:
                //get image from gallery
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_CODE);
                break;

        }

    }
    private void saveJournal() {
        String title = titleEditText.getText().toString().trim();
        String thought = thoughtsEditText.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thought)
                && imageUri != null){
            final StorageReference filepath = storageReference
                    .child("journal_image")//for the journal // images..image.png
                    .child("my_image_" + Timestamp.now().getSeconds());
            filepath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.INVISIBLE);
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    //WE WILL CREATE A JOURNAL OBJECT-MODEL

                                    Journal journal = new Journal();
                                    journal.setTitle(title);
                                    journal.setThought(thought);
                                    journal.setImageUrl(imageUrl);
                                    journal.setTimeAdded(new com.google.firebase.Timestamp(new Date()));
                                    journal.setUsername(currentUsername);
                                    journal.setUserId(currentUserId);
                                    //INVOKE OUR COLLECTION REFERENCE

                                    collectionReference.add(journal)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    startActivity(new Intent(QuestionActivity.this,
                                                            QuestionListActivity.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("TAG2", "onFailure: it failed nailssss "+e.getMessage());

                                                }
                                            });
                                    //SAVE A JOURNAL INSTANCE



                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d("TAG1", "onFailure: it failed here"+e.toString());

                        }
                    });


        }else {
            progressBar.setVisibility(View.INVISIBLE);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode  == RESULT_OK){
            if (data != null){
                imageUri =data.getData();
                imageView.setImageURI(imageUri);//show image

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }



}