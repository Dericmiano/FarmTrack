package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.farmtrack.adapter.ExpenseClickListener;
import com.example.farmtrack.adapter.ExpenseRecyclerViewAdapter;
import com.example.farmtrack.adapter.RecyclerViewAdapter;
import com.example.farmtrack.model.Expense;
import com.example.farmtrack.model.ExpenseSharedViewModel;
import com.example.farmtrack.model.ExpenseViewModel;
import com.example.farmtrack.model.SharedViewModel;
import com.example.farmtrack.model.TaskViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExpenditureActivity extends AppCompatActivity implements ExpenseClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    BottomSheetActivity bottomSheetActivity;
    private ExpenseViewModel expenseViewModel;
    private RecyclerView recyclerView;
    private ExpenseRecyclerViewAdapter expenseRecyclerViewAdapter;
    private ExpenseSharedViewModel expenseSharedViewModel;
    int counter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        bottomSheetActivity = new BottomSheetActivity();
        ConstraintLayout constraintLayout = findViewById(R.id.expenditure_bottom_sheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior
                .from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);



        recyclerView = findViewById(R.id.expenditure_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseViewModel = new ViewModelProvider.AndroidViewModelFactory(
                ExpenditureActivity.this.getApplication())
                .create(ExpenseViewModel.class);
        expenseSharedViewModel = new  ViewModelProvider(this)
                .get(ExpenseSharedViewModel.class);

        expenseViewModel.getAllExpenses().observe(this, expenses -> {

            expenseRecyclerViewAdapter = new ExpenseRecyclerViewAdapter(expenses, this);
            recyclerView.setAdapter(expenseRecyclerViewAdapter);


        });
        FloatingActionButton fab = findViewById(R.id.fabExpense);
        fab.setOnClickListener(view -> {

            showBottomSheetDialog();

        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_expenditure);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.news_nav_menu){
                startActivity(new Intent(ExpenditureActivity.this,NewsActivity.class));
            }else if (id ==R.id.plan_nav_menu){
                startActivity(new Intent(ExpenditureActivity.this,PlanActivity.class));
            }else if (id ==R.id.expense_nav_menu){
                startActivity(new Intent(ExpenditureActivity.this,ExpenditureActivity.class));
            }else if (id ==R.id.sales_nav_menu){
                startActivity(new Intent(ExpenditureActivity.this,SalesActivity.class));
            }else if (id ==R.id.tip_nav_menu){
                startActivity(new Intent(ExpenditureActivity.this,QuestionActivity.class));
            }


            return true;
        });




    }

    private void showBottomSheetDialog() {
        bottomSheetActivity.show(getSupportFragmentManager(),bottomSheetActivity.getTag());

    }

    @Override
    public void onTodoClick(Expense expense) {
        expenseSharedViewModel.selectItem(expense);
        expenseSharedViewModel.setIsEdit(true);
        showBottomSheetDialog();
    }

    @Override
    public void OnTodoDeleteButtonClick(Expense expense) {
        ExpenseViewModel.delete(expense);
        Log.d("TAG10", "OnTodoDeleteButtonClick: "+expense.getExpense());
        Toast.makeText(ExpenditureActivity.this, expense.getExpense()+"DELETED", Toast.LENGTH_LONG).show();
        expenseRecyclerViewAdapter.notifyDataSetChanged();


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
                    startActivity(new Intent(ExpenditureActivity.this, ProfileActivity.class));
//                    finish();
                }
                break;
            case R.id.action_home:
//                Take user to their journal
                startActivity(new Intent(ExpenditureActivity.this, HomePageActivity.class));
//                if (user != null && firebaseAuth != null){
//                    startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
////                    finish();
//                }
                break;
            case R.id.action_signout:
//                make user sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(ExpenditureActivity.this, MainActivity.class));
//                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}