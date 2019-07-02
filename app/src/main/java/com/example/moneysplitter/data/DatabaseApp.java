package com.example.moneysplitter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MeetingEntity.class, PersonEntity.class, GoalEntity.class}, version = 1, exportSchema = false)
public abstract class DatabaseApp extends RoomDatabase {

    private static DatabaseApp INSTANCE;

    public abstract MeetingDao meetingDao();
    public abstract PersonDao personDao();
    public abstract GoalDao goalDao();

    public static DatabaseApp getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                          DatabaseApp.class,
                                          "splitter.db")
                                          .allowMainThreadQueries()
                                          .build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
