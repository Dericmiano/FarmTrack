package com.example.farmtrack.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmtrack.R;
import com.example.farmtrack.model.Expense;
import com.example.farmtrack.utils.Utils;

import java.util.List;

public class SalesRecyclerViewAdapter extends RecyclerView.Adapter<SalesRecyclerViewAdapter.ViewHolder> {
    private final List<Expense> expenseList;

    public SalesRecyclerViewAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        String formatted = Utils.formatDate(expense.date);

        int total = (expense.getChemicals()+expense.getFertiliser()+expense.getLabour()+expense.getMachinery()
                +expense.getManure()+expense.getPloughing()+expense.getWater()+expense.getWeeding()+expense.getSeedling());
        holder.todayDate.setText(formatted);
        holder.expenseName.setText(expense.getExpense());
        holder.totalExpenditure.setText(String.valueOf(total));
        holder.totalSales.setText(String.valueOf(expense.getSales()));


    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView expenseName;
        public AppCompatTextView todayDate;
        public TextView totalSales;
        public TextView totalExpenditure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.sales_row_projName);
            todayDate = itemView.findViewById(R.id.sales_row_date);
            totalSales = itemView.findViewById(R.id.sales_row_totalSales);
            totalExpenditure = itemView.findViewById(R.id.sales_row_totalExpense);



        }
    }
}
