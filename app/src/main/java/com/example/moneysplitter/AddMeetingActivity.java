package com.example.moneysplitter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddMeetingActivity extends AppCompatActivity {
    private static final String TAG = "AddMeetingActivity";

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        //pobranie referencji do bazy
        final AppDatabase dbHelper = AppDatabase.getInstance(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Adding meeting to the list in MainActivity
        ((Button) findViewById(R.id.add_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addMeeting();
                long meetingId = getAddedMeetingId();

                Intent intent = new Intent(AddMeetingActivity.this, AddPersonActivity.class);
                intent.putExtra("meetingId", meetingId);
                startActivity(intent);

                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    private void addMeeting() {
        TextView nameView = (TextView) findViewById(R.id.meeting_name);
        TextView daysView = (TextView) findViewById(R.id.meeting_days);

        name = String.valueOf(nameView.getText());
        String days = String.valueOf(daysView.getText());

        if (name.length() == 0 || name.trim().equals("")) {
            Toast.makeText(AddMeetingActivity.this, R.string.name_validation_info, Toast.LENGTH_LONG).show();
            return;
        }

        if (days.equals("")) {
            days = "1";
        }

        ContentValues values = new ContentValues();
        values.put(MeetingTable.Column.NAME, name);
        values.put(MeetingTable.Column.DAYS, days);

        getContentResolver().insert(MeetingTable.CONTENT_URI, values);
        Log.d(TAG, "addMeeting: added name - " + name + ", days - " + days);
    }

    private long getAddedMeetingId() {
        Cursor cursor = getContentResolver().query(MeetingTable.CONTENT_URI,
                                                    new String[] {MeetingTable.Column._ID},
                                                    MeetingTable.Column.NAME + " = ?",
                                                    new String[] {name},
                                                    null);
        String meetingId = new String();

        if(cursor != null && cursor.moveToFirst()) {
            return Long.parseLong(cursor.getString(cursor.getColumnIndex(MeetingTable.Column._ID)).toString());
        }
        return 0;
    }
}