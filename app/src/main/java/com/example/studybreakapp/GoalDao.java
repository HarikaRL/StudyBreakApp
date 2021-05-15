package com.example.studybreakapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoalDao {

    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);

    @Delete
    void delete(Goal goal);

    @Query("UPDATE goal_table SET completionStatus=1 WHERE id =:id")
    void complete(int id);

    @Query("DELETE FROM goal_table")
    void deleteAllGoals();

    @Query("SELECT * FROM goal_table ORDER BY id DESC")
    LiveData<List<Goal>> getAllGoals();

    @Query("SELECT * FROM goal_table WHERE completionStatus = 0 ORDER BY id DESC")
    LiveData<List<Goal>> getIncompleteGoals();

    @Query("SELECT * FROM goal_table WHERE completionStatus = 1 ORDER BY id DESC")
    LiveData<List<Goal>> getCompleteGoals();


}
