package com.example.studybreakapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Goal.class}, version = 1)
public abstract class GoalDatabase extends RoomDatabase {

    private static GoalDatabase instance;

    public abstract GoalDao goalDao();

    public static synchronized GoalDatabase getInstance(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GoalDatabase.class, "goal_database").
                    fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GoalDao goalDao;

        private PopulateDbAsyncTask(GoalDatabase db) {
            goalDao = db.goalDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //goalDao.insert(new Goal("goalText1"));
            //goalDao.insert(new Goal("goalText2"));
            return null;
        }
    }


}
