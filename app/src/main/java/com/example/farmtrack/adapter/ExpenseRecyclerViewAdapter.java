package com.example.farmtrack.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.farmtrack.R;
import com.example.farmtrack.model.Expense;
import com.example.farmtrack.utils.Utils;

import java.util.List;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ViewHolder> {
    private final List<Expense> expenseList;
    private ExpenseClickListener penseClickListener;

    public ExpenseRecyclerViewAdapter(List<Expense> expenseList,ExpenseClickListener expenseClickListener) {
        this.expenseList = expenseList;
        this.penseClickListener = expenseClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenditure_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        String formatted = Utils.formatDate(expense.date);

        int total = (expense.getChemicals()+expense.getFertiliser()+expense.getLabour()+expense.getMachinery()
        +expense.getManure()+expense.getPloughing()+expense.getWater()+expense.getWeeding()+expense.getSeedling());


        holder.chemical.setText(String.valueOf(expense.getChemicals()));
        holder.manure.setText(String.valueOf(expense.getManure()));
        holder.machinery.setText(String.valueOf(expense.getMachinery()));
        holder.fertiliser.setText(String.valueOf(expense.getFertiliser()));
        holder.water.setText(String.valueOf(expense.getWater()));
        holder.weeding.setText(String.valueOf(expense.getWeeding()));
        holder.ploughing.setText(String.valueOf(expense.getPloughing()));
        holder.labour.setText(String.valueOf(expense.getLabour()));
        holder.seedlings.setText(String.valueOf(expense.getSeedling()));
        holder.todayDate.setText(formatted);
        holder.expense_name.setText(expense.getExpense());
        holder.total.setText(String.valueOf(total));



    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatTextView expense_name;
        public AppCompatTextView todayDate;
        public TextView chemical;
        public TextView fertiliser;
        public TextView machinery;
        public TextView labour;
        public TextView seedlings;
        public TextView weeding;
        public TextView water;
        public TextView manure;
        public TextView ploughing;
        public ImageButton deleteButton;
        public ConstraintLayout editButton;
        public TextView total;

        ExpenseClickListener expenseClickListener;

        public ViewHolder(@NonNull View itemView)  {
            super(itemView);
            expense_name = itemView.findViewById(R.id.expense_name_row);
            todayDate = itemView.findViewById(R.id.expense_date_row);
            chemical = itemView.findViewById(R.id.expense_chemical_row);
            fertiliser = itemView.findViewById(R.id.expense_fertiliser_row);
            machinery = itemView.findViewById(R.id.expense_machinery_row);
            labour = itemView.findViewById(R.id.expense_labour_row);
            seedlings = itemView.findViewById(R.id.expense_seedlings_row);
            weeding = itemView.findViewById(R.id.expense_weeding_row);
            water = itemView.findViewById(R.id.expense_water_row);
            manure = itemView.findViewById(R.id.expense_manure_row);
            ploughing = itemView.findViewById(R.id.expense_ploughing_row);
            deleteButton = itemView.findViewById(R.id.expense_delBtn_row);
            editButton = itemView.findViewById(R.id.expense_editButton_row);
            total = itemView.findViewById(R.id.expense_total_row);

            this.expenseClickListener = penseClickListener;

            deleteButton.setOnClickListener(this);
            editButton.setOnClickListener(this);





        }

        @Override
        public void onClick(View v) {
            Expense currExpense;
            int id = v.getId();
            if (id == R.id.expense_editButton_row){
                currExpense = expenseList.get(getAdapterPosition());
                expenseClickListener.onTodoClick(currExpense);
            }else  if (id == R.id.expense_delBtn_row){
                currExpense = expenseList.get(getAdapterPosition());
                expenseClickListener.OnTodoDeleteButtonClick(currExpense);

            }


        }
    }
}
