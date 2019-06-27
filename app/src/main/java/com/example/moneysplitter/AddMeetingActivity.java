package com.example.moneysplitter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneysplitter.data.MeetingTable;

public class AddMeetingActivity extends AppCompatActivity {
    private static final String TAG = "AddMeetingActivity";

    private String name;
    private String days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        //Adding meeting to the list in MainActivity
        ((Button) findViewById(R.id.add_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addMeetingtoDatabase();
                int meetingId = getAddedMeetingId();

                Intent intent = new Intent(AddMeetingActivity.this, AddPersonActivity.class);
                if(meetingId != 0) {
                    intent.putExtra("meetingId", meetingId);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddMeetingActivity.this, R.string.error_info, Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    private void addMeetingtoDatabase() {
        TextView nameView = findViewById(R.id.meeting_name);
        TextView daysView = findViewById(R.id.meeting_days);

        name = String.valueOf(nameView.getText());
        days = String.valueOf(daysView.getText());

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

    private int getAddedMeetingId() {
        Cursor cursor = getContentResolver().query(MeetingTable.CONTENT_URI,
                                                    new String[] {MeetingTable.Column._ID},
                                                    MeetingTable.Column.NAME + " = ?",
                                                    new String[] {name},
                                                    null);

        if(cursor != null && cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(0));
        }
        return 0;
    }
}