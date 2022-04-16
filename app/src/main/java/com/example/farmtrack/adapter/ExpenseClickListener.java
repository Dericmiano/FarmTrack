package com.example.farmtrack.adapter;


import com.example.farmtrack.model.Expense;

public interface ExpenseClickListener {
    void onTodoClick(Expense expense);
    void OnTodoDeleteButtonClick(Expense expense);
}
