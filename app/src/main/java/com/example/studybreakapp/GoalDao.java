package com.example.studybreakapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO)
 */
@Dao
public interface GoalDao {

    /**
     * Inserts a new goal to the SQL database
     * @param goal the new goal
     */
    @Insert
    void insert(Goal goal);

    /**
     * Updates an existing goal in the SQL database
     * @param goal the goal to update
     */
    @Update
    void update(Goal goal);

    /**
     * Deletes the specified goal from the SQL database
     * @param goal the goal to delete
     */
    @Delete
    void delete(Goal goal);

    /**
     * Marks the goal with the user-specified id as complete in the SQL database
     * @param id the id of the goal to be deleted
     */
    @Query("UPDATE goal_table SET completionStatus=1 WHERE id =:id")
    void complete(int id);

    /**
     * Deletes all goals in the SQL database
     */
    @Query("DELETE FROM goal_table")
    void deleteAllGoals();

    /**
     * Gets a list of all the goals in the SQL database
     * @return a list containing all the goals in the database
     */
    @Query("SELECT * FROM goal_table ORDER BY id DESC")
    LiveData<List<Goal>> getAllGoals();

    /**
     * Gets a list of all incomplete goals in the SQL database
     * @return a list containing all the incomplete goals in the database
     */
    @Query("SELECT * FROM goal_table WHERE completionStatus = 0 ORDER BY id DESC")
    LiveData<List<Goal>> getIncompleteGoals();

    /**
     * Gets a list of all the complete goals in the SQL database
     * @return a list containing all the complete goals in the database
     */
    @Query("SELECT * FROM goal_table WHERE completionStatus = 1 ORDER BY id DESC")
    LiveData<List<Goal>> getCompleteGoals();


}
