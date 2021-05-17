package com.example.studybreakapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * View model: used to store and manage the UI-related data
 */
public class GoalViewModel extends AndroidViewModel {
    private GoalRepository repository;
    private LiveData<List<Goal>> allGoals;
    private LiveData<List<Goal>> incompleteGoals;
    private LiveData<List<Goal>> completeGoals;

    /**
     * Parameter constructor:
     * Constructs a new goal viewmodel within the given application.
     * Constructs a new repository and asigns that to the "repository" variable.
     * Sets lists containing all goals, all incomplete goals, and all complete goals by calling the
     * repository's get methods
     * @param application the application
     */
    public GoalViewModel(@NonNull Application application) {
        super(application);
        repository = new GoalRepository(application);
        allGoals = repository.getAllGoals();
        incompleteGoals = repository.getIncompleteGoals();
        completeGoals = repository.getCompleteGoals();
    }

    /**
     * Inserts a new goal by calling the repository's insert method
     * @param goal the goal to insert
     */
    public void insert(Goal goal) {
        repository.insert(goal);
    }

    /**
     * Updates the specified  goal by calling the repository's update method
     * @param goal the goal to update
     */
    public void update(Goal goal) {
        repository.update(goal);
    }

    /**
     * Marks the specified goal as complete by calling the repository's complete method
     * @param goal the goal to complete
     */
    public void complete(Goal goal) {
        repository.complete(goal);
    }

    /**
     * Deletes the specified goal by calling the repository's delete method
     * @param goal the goal to delete
     */
    public void delete(Goal goal) {
        repository.delete(goal);
    }

    /**
     * Deletes all stored goals by calling the repository's delete method
     */
    public void deleteAllGoals() {
        repository.deleteAllGoals();
    }

    /**
     * Gets all the stored goals
     * @return a list containing all stored goals
     */
    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    /**
     * Gets all stored incomplete goals
     * @return a list containing all stored incomplete goals
     */
    public LiveData<List<Goal>> getIncompleteGoals() {
        return incompleteGoals;
    }

    /**
     * Gets all stored complete goals
     * @return a list containing all stored complete goals
     */
    public LiveData<List<Goal>> getCompleteGoals() {
        return completeGoals;
    }
}
