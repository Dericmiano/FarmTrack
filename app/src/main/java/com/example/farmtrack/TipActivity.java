package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.farmtrack.model.Tips;
import com.example.farmtrack.ui.TipRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TipActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private List<Tips> tipsList;
    private RecyclerView recyclerView;
    private TipRecyclerAdapter tipRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("Tips");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        tipsList = new ArrayList<>();
        recyclerView = findViewById(R.id.tip_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_tip);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.news_nav_menu){
                startActivity(new Intent(TipActivity.this,NewsActivity.class));
            }else if (id ==R.id.plan_nav_menu){
                startActivity(new Intent(TipActivity.this,PlanActivity.class));
            }else if (id ==R.id.expense_nav_menu){
                startActivity(new Intent(TipActivity.this,ExpenditureActivity.class));
            }else if (id ==R.id.sales_nav_menu){
                startActivity(new Intent(TipActivity.this,SalesActivity.class));
            }else if (id ==R.id.tip_nav_menu){
                startActivity(new Intent(TipActivity.this,QuestionActivity.class));
            }


            return true;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            for (QueryDocumentSnapshot tips:queryDocumentSnapshots){
                                Tips tips1 = tips.toObject(Tips.class);
                                tipsList.add(tips1);
                            }
                            tipRecyclerAdapter = new TipRecyclerAdapter(TipActivity.this,
                                    tipsList);
                            recyclerView.setAdapter(tipRecyclerAdapter);
                            tipRecyclerAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(TipActivity.this,
                                    "Empty Recycle adapter",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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
                    startActivity(new Intent(TipActivity.this, ProfileActivity.class));
//                    finish();
                }
                break;
            case R.id.action_home:
//                Take user to their journal
                startActivity(new Intent(TipActivity.this, HomePageActivity.class));
//                if (user != null && firebaseAuth != null){
//                    startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
////                    finish();
//                }
                break;
            case R.id.action_signout:
//                make user sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(TipActivity.this, MainActivity.class));
//                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}
























