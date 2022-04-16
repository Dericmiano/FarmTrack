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

import com.example.farmtrack.model.Expense2;
import com.example.farmtrack.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class SubmitionActivity extends AppCompatActivity {
    private EditText projectName;
    private EditText chemical;
    private EditText fertiliser;
    private EditText water;
    private EditText weeding;
    private EditText manure;
    private EditText ploughing;
    private EditText labour;
    private EditText machinery;
    private EditText seedling;
    private EditText sales;
    private ConstraintLayout submitButton;
    private ProgressBar progressBar;

    private String currentUserId;
    private String currentUsername;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    // connection to firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Expenses");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submition);
//        storageReference = FirebaseStorage.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        projectName = findViewById(R.id.sub_bot_ent_name);
        chemical = findViewById(R.id.sub_bot_ent_chemicals);
        fertiliser = findViewById(R.id.sub_bot_ent_fertiliser);
        weeding = findViewById(R.id.sub_bot_ent_weeding);
        manure = findViewById(R.id.sub_bot_ent_manure);
        machinery = findViewById(R.id.sub_bot_ent_machinery);
        ploughing = findViewById(R.id.sub_bot_ent_ploughing);
        labour = findViewById(R.id.sub_bot_ent_labour);
        seedling = findViewById(R.id.sub_bot_ent_seedling);
        water = findViewById(R.id.sub_bot_ent_water);
        sales = findViewById(R.id.sub_bot_ent_sales);
        submitButton = findViewById(R.id.sub_bot_ent_submit);


        progressBar.setVisibility(View.INVISIBLE);

        if (JournalApi.getInstance() != null){
            currentUserId = JournalApi.getInstance().getUserId();
            currentUsername = JournalApi.getInstance().getUsername();


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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJournal();
            }
        });




        String myName = getIntent().getStringExtra("expensename");
        String myChemical = getIntent().getStringExtra("theChemical");
        String myWeeding = getIntent().getStringExtra("theWeeding");
        String myPloughing = getIntent().getStringExtra("thePloughing");
        String myWater = getIntent().getStringExtra("theWater");
        String myFertiliser = getIntent().getStringExtra("theFertiliser");
        String myMachinery = getIntent().getStringExtra("theMachinery");
        String myLabour = getIntent().getStringExtra("theLabour");
        String mySeedlings = getIntent().getStringExtra("theSeedlings");
        String myManure = getIntent().getStringExtra("theManure");

        projectName.setText(myName);
        chemical.setText(myChemical);
        weeding.setText(myWeeding);
        manure.setText(myManure);
        ploughing.setText(myPloughing);
        water.setText(myWater);
        fertiliser.setText(myFertiliser);
        machinery.setText(myMachinery);
        labour.setText(myLabour);
        seedling.setText(mySeedlings);








    }

    private void saveJournal() {
        String name = projectName.getText().toString().trim();
        int myChemical =  Integer.parseInt(chemical.getText().toString());
        int myFertiliser =  Integer.parseInt(fertiliser.getText().toString());
        int mySeedling =  Integer.parseInt(seedling.getText().toString());
        int myWeeding =  Integer.parseInt(weeding.getText().toString());
        int myPloughing =  Integer.parseInt(ploughing.getText().toString());
        int myWater =  Integer.parseInt(water.getText().toString());
        int myLabour =  Integer.parseInt(labour.getText().toString());
        int myMachinery =  Integer.parseInt(machinery.getText().toString());
        int myManure =  Integer.parseInt(manure.getText().toString());
        int mySales = Integer.parseInt(sales.getText().toString());



//                helps to test empty inputs

        String nameText= projectName.getText().toString();
        String chemicalText= chemical.getText().toString();
        String fertiliserText= fertiliser.getText().toString();
        String seedlingText= seedling.getText().toString();
        String manureText= weeding.getText().toString();
        String labourText= ploughing.getText().toString();
        String machineryText= water.getText().toString();
        String waterText= manure.getText().toString();
        String ploughingText= labour.getText().toString();
        String weedingText = weeding.getText().toString();
        String salesText = sales.getText().toString();

        if (!TextUtils.isEmpty(nameText) || !TextUtils.isEmpty(chemicalText) || !TextUtils.isEmpty(fertiliserText) ||
                !TextUtils.isEmpty(seedlingText) || !TextUtils.isEmpty(manureText) || !TextUtils.isEmpty(labourText) ||
                !TextUtils.isEmpty(machineryText) || !TextUtils.isEmpty(waterText) || !TextUtils.isEmpty(ploughingText)||
                !TextUtils.isEmpty(weedingText) || !TextUtils.isEmpty(salesText)){
            Expense2 expense2 = new Expense2();
            expense2.setExpense(name);
            expense2.setUserId(currentUserId);
            expense2.setDate(new com.google.firebase.Timestamp(new Date()));
            expense2.setUsername(currentUsername);
            expense2.setChemicals(myChemical);
            expense2.setFertiliser(myFertiliser);
            expense2.setMachinery(myMachinery);
            expense2.setLabour(myLabour);
            expense2.setSeedling(mySeedling);
            expense2.setPloughing(myPloughing);
            expense2.setWater(myWater);
            expense2.setManure(myManure);
            expense2.setWeeding(myWeeding);
            expense2.setSales(mySales);

            collectionReference.add(expense2)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(SubmitionActivity.this,
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


        }else {
            progressBar.setVisibility(View.INVISIBLE);

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