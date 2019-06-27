package com.example.moneysplitter.data;

import android.content.ContentUris;
import android.net.Uri;

import static com.example.moneysplitter.data.AppProvider.CONTENT_AUTHORITY;
import static com.example.moneysplitter.data.AppProvider.CONTENT_AUTHORITY_URI;

public class ForWhatTable {
    public static final String TABLE_NAME = "ForWhat";

    public static class Column {
        public static final String _ID = "_id";
        public static final String NAME = "Name";
        public static final String CONCERN = "If_suit";
        public static final String ID_PERSON = "id_persons";
    }

    public static class PrefixColumn {
        public static final String _ID = TABLE_NAME + "." + Column._ID;
        public static final String NAME = TABLE_NAME + "." + Column.NAME;
        public static final String CONCERN = TABLE_NAME + "." + Column.CONCERN;
        public static final String ID_PERSON = TABLE_NAME + "." + Column.ID_PERSON;
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    static long getForWhatId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
