package com.example.farmtrack.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExpenseSharedViewModel extends ViewModel {
    private final MutableLiveData<Expense> selectedItem = new MutableLiveData<>();
    private boolean isEdit;

    public void selectItem(Expense expense){
        selectedItem.setValue(expense);
    }
    public LiveData<Expense> getSelectedItem(){
        return selectedItem;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

}
