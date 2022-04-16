package com.example.farmtrack.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.farmtrack.data.DoisterRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public  static DoisterRepository repository;
    public  final LiveData<List<Task>> allTasks;
    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new DoisterRepository(application);
        allTasks = repository.getAllTasks();
    }
    public LiveData<List<Task>> getAllTasks(){ return allTasks;}
    public static void insert(Task task){repository.insert(task);}
    public LiveData<Task> get(long id){return repository.get(id);}
    public static void update(Task task){repository.update(task);}
    public static void delete(Task task){repository.delete(task);}

}
