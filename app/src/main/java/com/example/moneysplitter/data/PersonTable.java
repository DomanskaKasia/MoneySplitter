package com.example.moneysplitter.data;

import android.content.ContentUris;
import android.net.Uri;

import static com.example.moneysplitter.data.AppProvider.CONTENT_AUTHORITY;
import static com.example.moneysplitter.data.AppProvider.CONTENT_AUTHORITY_URI;

public class PersonTable {
    public static final String TABLE_NAME = "Persons";

    public static class Column {
        public static final String _ID = "_id";
        public static final String NAME = "Name";
        public static final String DAYS = "Number_of_days";
        public static final String ID_MEETING = "id_meetings";
    }

    public static class PrefixColumn {
        public static final String _ID = TABLE_NAME + "." + Column._ID;
        public static final String NAME = TABLE_NAME + "." + Column.NAME;
        public static final String DAYS = TABLE_NAME + "." + Column.DAYS;
        public static final String ID_MEETING = TABLE_NAME + "." + Column.ID_MEETING;
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    static long getPersonsId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}