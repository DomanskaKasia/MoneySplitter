package com.example.moneysplitter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Basic database class for the application.
 * The only class that should use this is AppProvider.
 */
class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    private static final String DB_NAME = "splitter.db";
    private static final int DB_VERSION = 1;

    //Singleton implementation
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    /**
     * Get an instance of the app's singelton database helper object.
     * @param context the content providers context.
     * @return a SQLite database helper object.
     */
    static AppDatabase getInstance(Context context) {
        if(instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");

        String sql;
        sql = "CREATE TABLE " + MeetingTable.TABLE_NAME + " (" +
                MeetingTable.Column._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                MeetingTable.Column.NAME + " TEXT NOT NULL, " +
                MeetingTable.Column.DAYS + " INTEGER NOT NULL);";
        Log.d(TAG, sql);
        db.execSQL(sql);

        sql = "CREATE TABLE " + PersonTable.TABLE_NAME + " (" +
                PersonTable.Column._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                PersonTable.Column.NAME + " TEXT NOT NULL, " +
                PersonTable.Column.DAYS + " INTEGER NOT NULL, " +
                PersonTable.Column.ID_MEETING + " INTEGER);";
        Log.d(TAG, sql);
        db.execSQL(sql);

        sql = "CREATE TABLE " + ForWhatTable.TABLE_NAME + " (" +
                ForWhatTable.Column._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ForWhatTable.Column.NAME + " TEXT NOT NULL, " +
                ForWhatTable.Column.CONCERN + " INTEGER NOT NULL, " +
                ForWhatTable.Column.ID_PERSON + " INTEGER NOT NULL);";
        Log.d(TAG, sql);
        db.execSQL(sql);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                break;
            default:
                throw new IllegalStateException("Unknown new version " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }



    public static void createForWhatNamesView(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");

        String sql;
        sql = "CREATE VIEW IF NOT EXISTS ForWhatNames AS SELECT DISTINCT " + ForWhatTable.Column.NAME +
                " FROM " + ForWhatTable.TABLE_NAME + ";";
        Log.d(TAG, sql);
        db.execSQL(sql);

        Log.d(TAG, "onCreate: ends");
    }

    public static void deleteForWhatNamesView(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");

        String sql;
        sql = "DROP VIEW IF EXISTS ForWhatNames;";
        Log.d(TAG, sql);
        db.execSQL(sql);

        Log.d(TAG, "onCreate: ends");
    }
}