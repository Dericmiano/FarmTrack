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

import com.example.farmtrack.model.News;
import com.example.farmtrack.ui.NewsRecyclerAdapter;
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

public class NewsActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<News> newsList;
    private RecyclerView recyclerView;
    private NewsRecyclerAdapter newsRecyclerAdapter;


    private CollectionReference collectionReference = db.collection("News");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
        setContentView(R.layout.activity_news);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        newsList = new ArrayList<>();
        recyclerView = findViewById(R.id.news_recycleViews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_news);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.news_nav_menu){
                startActivity(new Intent(NewsActivity.this,NewsActivity.class));
            }else if (id ==R.id.plan_nav_menu){
                startActivity(new Intent(NewsActivity.this,PlanActivity.class));
            }else if (id ==R.id.expense_nav_menu){
                startActivity(new Intent(NewsActivity.this,ExpenditureActivity.class));
            }else if (id ==R.id.sales_nav_menu){
                startActivity(new Intent(NewsActivity.this,SalesActivity.class));
            }else if (id ==R.id.tip_nav_menu){
                startActivity(new Intent(NewsActivity.this,QuestionActivity.class));
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
                            for (QueryDocumentSnapshot news : queryDocumentSnapshots){
                                News news1 = news.toObject(News.class);
                                newsList.add(news1);
                            }
                            newsRecyclerAdapter = new NewsRecyclerAdapter(NewsActivity.this,
                                    newsList);
                            recyclerView.setAdapter(newsRecyclerAdapter);
                            newsRecyclerAdapter.notifyDataSetChanged();

                        }else {
                            Toast.makeText(NewsActivity.this,
                                    "no news now", Toast.LENGTH_SHORT).show();
                        }}
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
                    startActivity(new Intent(NewsActivity.this, ProfileActivity.class));
//                    finish();
                }
                break;
            case R.id.action_home:
//                Take user to their journal
                startActivity(new Intent(NewsActivity.this, HomePageActivity.class));
//                if (user != null && firebaseAuth != null){
//                    startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
////                    finish();
//                }
                break;
            case R.id.action_signout:
//                make user sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(NewsActivity.this, MainActivity.class));
//                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}




















