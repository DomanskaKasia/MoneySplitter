package com.example.moneysplitter;

import android.content.ContentValues;
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
        sql = "CREATE TABLE " + Meeting.TABLE_NAME + " (" +
                Meeting.Column.ID + " INTEGER PRIMARY KEY NOT NULL, " +
                Meeting.Column.NAME + " TEXT NOT NULL, " +
                Meeting.Column.DAYS + " INTEGER NOT NULL);";
        Log.d(TAG, sql);
        db.execSQL(sql);
        insertMeeting(db, "Majowy 2018", 5);
        insertMeeting(db, "Sylwester 2018/19", 3);
        insertMeeting(db, "Lutowy 2019", 4);

        sql = "CREATE TABLE " + Person.TABLE_NAME + " (" +
                Person.Column.ID + " INTEGER PRIMARY KEY NOT NULL, " +
                Person.Column.NAME + " TEXT NOT NULL, " +
                Person.Column.DAYS + " INTEGER NOT NULL, " +
                Person.Column.ID_MEETING + " INTEGER);";
        Log.d(TAG, sql);
        db.execSQL(sql);

        sql = "CREATE TABLE " + ForWhat.TABLE_NAME + " (" +
                ForWhat.Column.ID + " INTEGER PRIMARY KEY NOT NULL, " +
                ForWhat.Column.NAME + " TEXT NOT NULL, " +
                ForWhat.Column.CONCERN + " INTEGER NOT NULL, " +
                ForWhat.Column.ID_PERSON + " INTEGER NOT NULL);";
        Log.d(TAG, sql);
        db.execSQL(sql);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");

        Log.d(TAG, "onUpgrade: ends");
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "updateDatabase: starts");

        Log.d(TAG, "updateDatabase: ends");
    }



    public static int insertMeeting(SQLiteDatabase db, String name, int days) {
        Log.d(TAG, "insertMeeting: starts");

        ContentValues m = new ContentValues();
        m.put(Meeting.Column.NAME, name);
        m.put(Meeting.Column.DAYS, days);
        return (int) db.insert(Meeting.TABLE_NAME, null, m);
    }

    private static void uprateMeeting(SQLiteDatabase db, int id, String name, int days) {
        Log.d(TAG, "uprateMeetingName: starts");

        ContentValues m = new ContentValues();
        m.put(Meeting.Column.NAME, name);
        m.put(Meeting.Column.DAYS, days);

        db.update(Meeting.TABLE_NAME, m, "_id = ?", new String[] {Integer.toString(id)});
    }

    private static void deleteMeeting(SQLiteDatabase db, int id) {
        Log.d(TAG, "deleteMeetingRow: starts");

        db.delete(Meeting.TABLE_NAME, "_id = ?", new String[] {Integer.toString(id)});

        Log.d(TAG, "deleteMeetingRow: ends");
    }



    public static void insertPerson(SQLiteDatabase db, String name, int days, int id) {
        Log.d(TAG, "insertPerson: starts");

        ContentValues m = new ContentValues();
        m.put(Person.Column.NAME, name);
        m.put(Person.Column.DAYS, days);
        m.put(Person.Column.ID_MEETING, id);
        db.insert(Person.TABLE_NAME, null, m);

        Log.d(TAG, "insertPerson: added name: " + name + ", days: " + days);
        Log.d(TAG, "insertPerson: ends");
    }



    public static void insertForWhat(SQLiteDatabase db, String name, int id) {
        Log.d(TAG, "insertForWhat: starts");

        ContentValues m = new ContentValues();
        m.put(ForWhat.Column.NAME, name);
        m.put(ForWhat.Column.CONCERN, true);
        m.put(ForWhat.Column.ID_PERSON, id);
        db.insert(ForWhat.TABLE_NAME, null, m);

        Log.d(TAG, "insertForWhat: added _name: " + name + ", concern: true, id_person: " + id);
        Log.d(TAG, "insertForWhat: ends");
    }



    public static void createForWhatNamesView(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");

        String sql;
        sql = "CREATE VIEW IF NOT EXISTS ForWhatNames AS SELECT DISTINCT " + ForWhat.Column.NAME +
                " FROM " + ForWhat.TABLE_NAME + ";";
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
