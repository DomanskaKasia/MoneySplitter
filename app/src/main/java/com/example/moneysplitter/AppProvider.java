package com.example.moneysplitter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;

public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";

    private AppDatabase dbHelper;

    static final String CONTENT_AUTHORITY = "com.example.moneysplitter.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int MEETINGS = 100;
    private static final int MEETINGS_ID = 101;
    private static final int PERSONS = 200;
    private static final int PERSONS_ID = 201;
    private static final int FORWHAT = 300;
    private static final int FORWHAT_ID = 301;
    private static final int FORWHAT_FROM_MEETING = 400;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, MeetingTable.TABLE_NAME, MEETINGS);
        uriMatcher.addURI(CONTENT_AUTHORITY, MeetingTable.TABLE_NAME + "/#", MEETINGS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PersonTable.TABLE_NAME, PERSONS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PersonTable.TABLE_NAME + "/#", PERSONS_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, ForWhatTable.TABLE_NAME, FORWHAT);
        uriMatcher.addURI(CONTENT_AUTHORITY, ForWhatTable.TABLE_NAME + "/#", FORWHAT_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, ForWhatFromMeeting.TABLE_NAME, FORWHAT_FROM_MEETING);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: called");
        dbHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: called with uri " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case MEETINGS:
                queryBuilder.setTables(MeetingTable.TABLE_NAME);
                break;
            case MEETINGS_ID:
                queryBuilder.setTables(MeetingTable.TABLE_NAME);
                long meetingId = MeetingTable.getMeetingId(uri);
                queryBuilder.appendWhere(MeetingTable.Column._ID + " = " + meetingId);
                break;
            case PERSONS:
                queryBuilder.setTables(PersonTable.TABLE_NAME);
                break;
            case PERSONS_ID:
                queryBuilder.setTables(PersonTable.TABLE_NAME);
                long personId = PersonTable.getPersonsId(uri);
                queryBuilder.appendWhere(PersonTable.Column._ID + " = " + personId);
                break;
            case FORWHAT:
                queryBuilder.setTables(ForWhatTable.TABLE_NAME);
                break;
            case FORWHAT_ID:
                queryBuilder.setTables(ForWhatTable.TABLE_NAME);
                long forWhatId = ForWhatTable.getForWhatId(uri);
                queryBuilder.appendWhere(ForWhatTable.Column._ID + " = " + forWhatId);
                break;
            case FORWHAT_FROM_MEETING:
                queryBuilder.setTables(ForWhatTable.TABLE_NAME + " INNER JOIN " +
                                PersonTable.TABLE_NAME + " ON " +
                                ForWhatTable.PrefixColumn.ID_PERSON + " = " + PersonTable.PrefixColumn._ID +
                                " INNER JOIN " +
                                MeetingTable.TABLE_NAME + " ON " +
                                PersonTable.PrefixColumn.ID_MEETING + " = " + MeetingTable.PrefixColumn._ID );
//                queryBuilder.appendWhere(MeetingTable.PrefixColumn._ID + " = ?";
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        try {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (NullPointerException e) {
            Log.d(TAG, "query: cannot get content resolver");
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: called");
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match is: " + match);

        switch (match) {
            case MEETINGS:
                return MeetingTable.CONTENT_TYPE;
            case MEETINGS_ID:
                return MeetingTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: called with uri " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        final SQLiteDatabase db;
        Uri newUri;
        long recordId;

        switch (match) {
            case MEETINGS:
                db = dbHelper.getWritableDatabase();
                recordId = db.insert(MeetingTable.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    newUri = MeetingTable.buildUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;
            case PERSONS:
                db = dbHelper.getWritableDatabase();
                recordId = db.insert(PersonTable.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    newUri = PersonTable.buildUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;
            case FORWHAT:
                db = dbHelper.getWritableDatabase();
                recordId = db.insert(ForWhatTable.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    newUri = ForWhatTable.buildUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri);
        }

        if(recordId >= 0) {
            Log.d(TAG, "insert: setting notify change with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "insert: return uri " + newUri);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: called with uri: " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        final SQLiteDatabase db;
        int count;
        StringBuilder criteria;

        switch (match) {
            case MEETINGS:
                db = dbHelper.getWritableDatabase();
                count = db.delete(MeetingTable.TABLE_NAME, selection, selectionArgs);
                break;
            case MEETINGS_ID:
                db = dbHelper.getWritableDatabase();
                long meetingId = MeetingTable.getMeetingId(uri);
                criteria = new StringBuilder(MeetingTable.Column._ID + " = " + meetingId);

                if(selection != null && selection.length() > 0) {
                    criteria.append(" AND (" + selection + ")");
                }

                count = db.delete(MeetingTable.TABLE_NAME, criteria.toString(), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if(count > 0) {
            Log.d(TAG, "delete: setting notify change with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "delete: return count " + count);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update: called with uri " + uri);
        final int match = uriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        final SQLiteDatabase db;
        int count;
        StringBuilder criteria;

        switch (match) {
            case MEETINGS:
                db = dbHelper.getWritableDatabase();
                count = db.update(MeetingTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MEETINGS_ID:
                db = dbHelper.getWritableDatabase();
                long meetingId = MeetingTable.getMeetingId(uri);
                criteria = new StringBuilder(MeetingTable.Column._ID + " = " + meetingId);

                if(selection != null && selection.length() > 0) {
                    criteria.append(" AND (" + selection + ")");
                }

                count = db.update(MeetingTable.TABLE_NAME, values, criteria.toString(), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if(count > 0) {
            Log.d(TAG, "update: setting notify change with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "update: return count " + count);
        return count;
    }
}
