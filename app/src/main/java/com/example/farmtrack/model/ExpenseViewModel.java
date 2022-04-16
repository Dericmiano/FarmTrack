package com.example.farmtrack.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.farmtrack.data.ExpenseRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    public  static ExpenseRepository repository;
    public  final LiveData<List<Expense>> allExpenses;
    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allExpenses = repository.getAllTasks();
    }
    public LiveData<List<Expense>> getAllExpenses(){ return allExpenses;}
    public static void insert(Expense expense){repository.insert(expense);}
    public LiveData<Expense> get(long id){return repository.get(id);}
    public static void update(Expense expense){repository.update(expense);}
    public static void delete(Expense expense){repository.delete(expense);}

}
