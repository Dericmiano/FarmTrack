package com.example.farmtrack.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farmtrack.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpense(Expense expense);

    @Query("DELETE  FROM expense_table")
    void deleteAll();

    @Query("SELECT * FROM expense_table")
    LiveData<List<Expense>> getExpenses();

    @Query("SELECT * FROM expense_table WHERE expense_table.expense_id == :id")
    LiveData<Expense> get(long id);

    @Update
    void update(Expense task);

    @Delete
    void delete(Expense task);
}
