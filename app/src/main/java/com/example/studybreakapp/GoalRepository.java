package com.example.studybreakapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Repository: interacts with the DAO
 */
public class GoalRepository {
    private GoalDao goalDao;
    private LiveData<List<Goal>> allGoals;
    private LiveData<List<Goal>> incompleteGoals;
    private LiveData<List<Goal>> completeGoals;

    /**
     * Parameter constructor:
     * Constructs a new repository in the given application
     * @param application the user-provided application
     */
    public GoalRepository(Application application) {
        GoalDatabase database = GoalDatabase.getInstance(application);
        goalDao = database.goalDao();
        allGoals = goalDao.getAllGoals();
        incompleteGoals = goalDao.getIncompleteGoals();
        completeGoals = goalDao.getCompleteGoals();
    }

    /**
     * Inserts a new goal by calling the InsertGoalAsyncTask helper method
     * @param goal the goal to insert
     */
    public void insert(Goal goal) {
        new InsertGoalAsyncTask(goalDao).execute(goal);
    }

    /**
     * Updates the specified goal by calling the UpdateGoalAsyncTask helper method
     * @param goal the goal to update
     */
    public void update(Goal goal) {
        new UpdateGoalAsyncTask(goalDao).execute(goal);
    }

    /**
     * Marks the specified goal as complete by calling the CompleteGoalAsyncTask helper method
     * @param goal the goal to mark as complete
     */
    public void complete(Goal goal) {
        new CompleteGoalAsyncTask(goalDao).execute(goal);
    }

    /**
     * Deletes the specifed goal by calling the DeleteGoalAsyncTask helper method
     * @param goal the goal to delete
     */
    public void delete(Goal goal) {
        new DeleteGoalAsyncTask(goalDao).execute(goal);
    }

    /**
     * Deletes all goals by calling the DeleteAllGoalsAsyncTask helper method
     */
    public void deleteAllGoals() {
        new DeleteAllGoalsAsyncTask(goalDao).execute();
    }

    /**
     * Gets all the stored goals
     * @return a list containing all the stored goals
     */
    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    /**
     * Gets all the stored incomplete goals
     * @return a list containing all the stored incomplete goals
     */
    public LiveData<List<Goal>> getIncompleteGoals() {
        return incompleteGoals;
    }

    /**
     * Gets all the stored complete goals
     * @return a list containing all the stored complete goals
     */
    public LiveData<List<Goal>> getCompleteGoals() {
        return completeGoals;
    }

    /**
     * Helper method that connects the repository's insert method to the DAO insert method
     */
    private static class InsertGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private InsertGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.insert(goals[0]);
            return null;
        }
    }

    /**
     * Helper method that connects the repository's update method to the DAO update method
     */
    private static class UpdateGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private UpdateGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.update(goals[0]);
            return null;
        }
    }

    /**
     * Helper method that connects the repository's complete method to the DAO complete method
     */
    private static class CompleteGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private CompleteGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.complete(goals[0].getId());
            return null;
        }
    }

    /**
     * Helper method that connects the repository's delete method to the DAO delete method
     */
    private static class DeleteGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private DeleteGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.delete(goals[0]);
            return null;
        }
    }

    /**
     * Helper method that connects the repository's delete all method to the DAO delete all method
     */
    private static class DeleteAllGoalsAsyncTask extends AsyncTask<Void, Void, Void> {
        private GoalDao goalDao;

        private DeleteAllGoalsAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            goalDao.deleteAllGoals();
            return null;
        }
    }
}

