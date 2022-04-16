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
import android.view.View;

import com.example.farmtrack.adapter.OnTodoClickListener;
import com.example.farmtrack.adapter.RecyclerViewAdapter;
import com.example.farmtrack.model.SharedViewModel;
import com.example.farmtrack.model.Task;
import com.example.farmtrack.model.TaskViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PlanActivity extends AppCompatActivity implements OnTodoClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private int counter;
    PlanBottomSheetLayout bottomSheetFragment;
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedViewModel sharedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        counter = 0;
        bottomSheetFragment = new PlanBottomSheetLayout();
        ConstraintLayout constraintLayout = findViewById(R.id.planBottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior
                .from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        recyclerView = findViewById(R.id.plan_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                PlanActivity.this.getApplication())
                .create(TaskViewModel.class);
        sharedViewModel = new  ViewModelProvider(this)
                .get(SharedViewModel.class);


        taskViewModel.getAllTasks().observe(this, tasks -> {
//            for (Task task:tasks){
//                Log.d("TAG3", "onCreate: "+task.getTaskId());
//            }
            recyclerViewAdapter = new RecyclerViewAdapter(tasks,this);
            recyclerView.setAdapter(recyclerViewAdapter);


        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
//            Task task = new Task("Task"+ counter++, Priority.MEDIUM, Calendar.getInstance().getTime(),
//                    Calendar.getInstance().getTime(), false);
//            TaskViewModel.insert(task);
            showBottomSheetDialog();

        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_plan);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.news_nav_menu){
                startActivity(new Intent(PlanActivity.this,NewsActivity.class));
            }else if (id ==R.id.plan_nav_menu){
                startActivity(new Intent(PlanActivity.this,PlanActivity.class));
            }else if (id ==R.id.expense_nav_menu){
                startActivity(new Intent(PlanActivity.this,ExpenditureActivity.class));
            }else if (id ==R.id.sales_nav_menu){
                startActivity(new Intent(PlanActivity.this,SalesActivity.class));
            }else if (id ==R.id.tip_nav_menu){
                startActivity(new Intent(PlanActivity.this,QuestionActivity.class));
            }


            return true;
        });
    }


    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setIsEdit(true);
//        Log.d("click", "onTodoClick: "+task.getTask());
        showBottomSheetDialog();;

    }

    @Override
    public void OnTodoRadioButtonClick(Task task) {
        Log.d("click", "onTodoradiobutoonClick: "+task.getTask());
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();


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
                    startActivity(new Intent(PlanActivity.this, ProfileActivity.class));
//                    finish();
                }
                break;
            case R.id.action_home:
//                Take user to their journal
                startActivity(new Intent(PlanActivity.this, HomePageActivity.class));
//                if (user != null && firebaseAuth != null){
//                    startActivity(new Intent(HomePageActivity.this, ProfileActivity.class));
////                    finish();
//                }
                break;
            case R.id.action_signout:
//                make user sign out
                if (user != null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    startActivity(new Intent(PlanActivity.this, MainActivity.class));
//                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }
}