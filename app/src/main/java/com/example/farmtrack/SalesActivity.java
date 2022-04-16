package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.farmtrack.adapter.ExpenseRecyclerViewAdapter;
import com.example.farmtrack.adapter.SalesRecyclerViewAdapter;
import com.example.farmtrack.model.ExpenseSharedViewModel;
import com.example.farmtrack.model.ExpenseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SalesActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private ExpenseViewModel expenseViewModel;
    private RecyclerView recyclerView;
    SalesRecyclerViewAdapter salesRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        recyclerView = findViewById(R.id.sales_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                SalesActivity.this.getApplication())
                .create(ExpenseViewModel.class);

        expenseViewModel.getAllExpenses().observe(this, expenses -> {

            salesRecyclerViewAdapter = new SalesRecyclerViewAdapter(expenses );
            recyclerView.setAdapter(salesRecyclerViewAdapter);


        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_sales);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.news_nav_menu){
                startActivity(new Intent(SalesActivity.this,NewsActivity.class));
            }else if (id ==R.id.plan_nav_menu){
                startActivity(new Intent(SalesActivity.this,PlanActivity.class));
            }else if (id ==R.id.expense_nav_menu){
                startActivity(new Intent(SalesActivity.this,ExpenditureActivity.class));
            }else if (id ==R.id.sales_nav_menu){
                startActivity(new Intent(SalesActivity.this,SalesActivity.class));
            }else if (id ==R.id.tip_nav_menu){
                startActivity(new Intent(SalesActivity.this,QuestionActivity.class));
            }


            return true;
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
                    startActivity(new Intent(SalesActivity.this, ProfileActivity.class));
//                    finish();
                }
                break;
            case R.id.action_home:
//                Take user to their journal
                startActivity(new Intent(SalesActivity.this, HomePageActivity.class));
//                if (user != null && firebaseAuth != null){
//                    startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
////                    finish();
//                }
                break;
            case R.id.action_signout:
//                make user sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(SalesActivity.this, MainActivity.class));
//                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}