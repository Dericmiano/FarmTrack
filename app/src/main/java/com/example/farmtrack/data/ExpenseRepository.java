package com.example.farmtrack.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.farmtrack.model.Expense;
import com.example.farmtrack.util.ExpenseRoomDatabase;

import java.util.List;

public class ExpenseRepository {
    private final ExpenseDao expenseDao;
    private final LiveData<List<Expense>> allExpenses;

    public ExpenseRepository(Application application) {
        ExpenseRoomDatabase database = ExpenseRoomDatabase.getDatabase(application);
        expenseDao = database.expenseDao();
        allExpenses = expenseDao.getExpenses();
    }
    public LiveData<List<Expense>> getAllTasks(){
        return  allExpenses;
    }
    public void insert(Expense expense){
        ExpenseRoomDatabase.databaseWriteExecutor.execute(() ->
                expenseDao.insertExpense(expense));
    }
    public LiveData<Expense> get(long id){
        return expenseDao.get(id);
    }
    public void update(Expense expense){
        ExpenseRoomDatabase.databaseWriteExecutor.execute(() -> expenseDao.update(expense)  );
    }
    public void delete(Expense expense){
        ExpenseRoomDatabase.databaseWriteExecutor.execute(() -> expenseDao.delete(expense));
    }
}
