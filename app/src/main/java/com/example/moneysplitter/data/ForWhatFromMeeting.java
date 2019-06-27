package com.example.moneysplitter.data;

import android.content.ContentUris;
import android.net.Uri;

import static com.example.moneysplitter.data.AppProvider.CONTENT_AUTHORITY;
import static com.example.moneysplitter.data.AppProvider.CONTENT_AUTHORITY_URI;

public class ForWhatFromMeeting {
    public static final String TABLE_NAME = "ForWhatFromMeeting";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    static long getForWhatFromMeetingId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
