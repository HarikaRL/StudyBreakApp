package com.example.studybreakapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class GoalViewModel extends AndroidViewModel {
    private GoalRepository repository;
    private LiveData<List<Goal>> allGoals;
    private LiveData<List<Goal>> incompleteGoals;
    private LiveData<List<Goal>> completeGoals;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        repository = new GoalRepository(application);
        allGoals = repository.getAllGoals();
        incompleteGoals = repository.getIncompleteGoals();
        completeGoals = repository.getCompleteGoals();
    }

    public void insert(Goal goal) {
        repository.insert(goal);
    }

    public void update(Goal goal) {
        repository.update(goal);
    }

    public void complete(Goal goal) {
        repository.complete(goal);
    }

    public void delete(Goal goal) {
        repository.delete(goal);
    }

    public void deleteAllGoals() {
        repository.deleteAllGoals();
    }

    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    public LiveData<List<Goal>> getIncompleteGoals() {
        return incompleteGoals;
    }

    public LiveData<List<Goal>> getCompleteGoals() {
        return completeGoals;
    }
}
