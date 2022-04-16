package com.example.farmtrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmtrack.model.Expense;
import com.example.farmtrack.model.ExpenseSharedViewModel;
import com.example.farmtrack.model.ExpenseViewModel;
import com.example.farmtrack.util.JournalApi;
import com.example.farmtrack.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Date;

public class BottomSheetActivity  extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText projectName;
    private EditText chemical;
    private EditText fertiliser;
    private EditText water;
    private EditText weeding;
    private EditText manure;
    private EditText ploughing;
    private EditText labour;
    private EditText machinery;
    private EditText seedling;
    private EditText sales;
    private ConstraintLayout saveButton;
    private ConstraintLayout submitButton;
    private Date date;
    private ImageView dateButton;
    Calendar calendar = Calendar.getInstance();
    private boolean isEdit;
    private TextView dateView;
    private ExpenseSharedViewModel expenseSharedViewModel;




    public BottomSheetActivity(){

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.activity_bottom_sheet, container, false);
        projectName = view.findViewById(R.id.exp_bot_ent_name);
        chemical = view.findViewById(R.id.exp_bot_ent_chemicals);
        fertiliser = view.findViewById(R.id.exp_bot_ent_fertiliser);
        weeding = view.findViewById(R.id.exp_bot_ent_weeding);
        manure = view.findViewById(R.id.exp_bot_ent_manure);
        machinery = view.findViewById(R.id.exp_bot_ent_machinery);
        ploughing = view.findViewById(R.id.exp_bot_ent_ploughing);
        labour = view.findViewById(R.id.exp_bot_ent_labour);
        seedling = view.findViewById(R.id.exp_bot_ent_seedling);
        water = view.findViewById(R.id.exp_bot_ent_water);
        dateView = view.findViewById(R.id.exp_bot_ent_date);
        sales = view.findViewById(R.id.exp_bot_ent_sales);


        submitButton = view.findViewById(R.id.exp_bot_ent_submit);

        saveButton = view.findViewById(R.id.exp_bot_saveButton);
        dateButton = view.findViewById(R.id.exp_bot_ent_dateButton);
        dateButton.setOnClickListener(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsSaved();
            }

            private void getDetailsSaved() {
                Intent intent = new Intent(getActivity(),
                        SubmitionActivity.class);
                intent.putExtra("expensename",projectName.getText());
                intent.putExtra("theChemical", chemical.getText());
                intent.putExtra("theWeeding", weeding.getText());
                intent.putExtra("thePloughing", ploughing.getText());
                intent.putExtra("theWater", water.getText());
                intent.putExtra("theFertiliser", fertiliser.getText());
                intent.putExtra("theMachinery", machinery.getText());
                intent.putExtra("theLabour", labour.getText());
                intent.putExtra("theSeedlings", seedling.getText());
                intent.putExtra("theManure", manure.getText());
                startActivity(intent);

                Log.d("3", "getDetailsSaved: "+projectName.getText());
            }
        });


        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        if (expenseSharedViewModel.getSelectedItem().getValue() != null){

            isEdit = expenseSharedViewModel.getIsEdit();
            Expense expense = expenseSharedViewModel.getSelectedItem().getValue();
            String formatted = Utils.formatDate(expense.date);
            projectName.setText(expense.getExpense());
            chemical.setText(String.valueOf(expense.getChemicals()));
            weeding.setText(String.valueOf(expense.getWeeding()));
            machinery.setText(String.valueOf(expense.getMachinery()));
            manure.setText(String.valueOf(expense.getManure()));
            ploughing.setText(String.valueOf(expense.getPloughing()));
            water.setText(String.valueOf(expense.getWater()));
            labour.setText(String.valueOf(expense.getLabour()));
            fertiliser.setText(String.valueOf(expense.getFertiliser()));
            seedling.setText(String.valueOf(expense.getSeedling()));
            sales.setText(String.valueOf(expense.getSales()));
            dateView.setText(formatted);
        }


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        expenseSharedViewModel = new ViewModelProvider(requireActivity())
                .get(ExpenseSharedViewModel.class);

        saveButton.setOnClickListener(v -> {

            String name =  projectName.getText().toString().trim();
            int myChemical =  Integer.parseInt(chemical.getText().toString());
            int myFertiliser =  Integer.parseInt(fertiliser.getText().toString());
            int mySeedling =  Integer.parseInt(seedling.getText().toString());
            int myWeeding =  Integer.parseInt(weeding.getText().toString());
            int myPloughing =  Integer.parseInt(ploughing.getText().toString());
            int myWater =  Integer.parseInt(water.getText().toString());
            int myLabour =  Integer.parseInt(labour.getText().toString());
            int myMachinery =  Integer.parseInt(machinery.getText().toString());
            int myManure =  Integer.parseInt(manure.getText().toString());
            int mySales = Integer.parseInt(sales.getText().toString());



//                helps to test empty inputs

            String nameText= projectName.getText().toString();
            String chemicalText= chemical.getText().toString();
            String fertiliserText= fertiliser.getText().toString();
            String seedlingText= seedling.getText().toString();
            String manureText= weeding.getText().toString();
            String labourText= ploughing.getText().toString();
            String machineryText= water.getText().toString();
            String waterText= manure.getText().toString();
            String ploughingText= labour.getText().toString();
            String weedingText = weeding.getText().toString();
            String salesText = sales.getText().toString();


            if (TextUtils.isEmpty(nameText) || TextUtils.isEmpty(chemicalText) || TextUtils.isEmpty(fertiliserText) ||
            TextUtils.isEmpty(seedlingText) || TextUtils.isEmpty(manureText) || TextUtils.isEmpty(labourText) ||
            TextUtils.isEmpty(machineryText) || TextUtils.isEmpty(waterText) || TextUtils.isEmpty(ploughingText)||
                    TextUtils.isEmpty(weedingText) || TextUtils.isEmpty(salesText)){
                Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG)
                        .show();
            }else {
                Expense expense = new Expense(
                        name,date,myChemical,myFertiliser,myMachinery,myLabour,mySeedling,
                        myPloughing,myWater,myManure,
                        myWeeding, mySales);
                if (isEdit) {
                    Expense updateExpense = expenseSharedViewModel.getSelectedItem().getValue();
                    assert updateExpense != null;
                    updateExpense.setExpense(name);
                    updateExpense.setChemicals(myChemical);
                    updateExpense.setFertiliser(myFertiliser);
                    updateExpense.setDate(date);
                    updateExpense.setWater(myWater);
                    updateExpense.setWeeding(myWeeding);
                    updateExpense.setMachinery(myMachinery);
                    updateExpense.setLabour(myLabour);
                    updateExpense.setPloughing(myPloughing);
                    updateExpense.setSeedling(mySeedling);
                    updateExpense.setManure(myManure);
                    updateExpense.setSales(mySales);
                    ExpenseViewModel.update(updateExpense);
                    expenseSharedViewModel.setIsEdit(false);
                }else {
                    ExpenseViewModel.insert(expense);
                }
                chemical.setText("");
                projectName.setText("");
                fertiliser.setText("");
                water.setText("");
                weeding.setText("");
                ploughing.setText("");
                labour.setText("");
                manure.setText("");
                machinery.setText("");
                dateView.setText("");
                sales.setText("");

//                    if (this.isVisible()){
//                        this.dismiss();
//                    }
            }

        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.exp_bot_ent_dateButton){
            calendar.add(Calendar.DAY_OF_MONTH, 0);
            date = calendar.getTime();
            Log.d("time", "onClick: "+date.toString());
            String formatted = Utils.formatDate(date);
            dateView.setText(formatted);
        }
    }




}