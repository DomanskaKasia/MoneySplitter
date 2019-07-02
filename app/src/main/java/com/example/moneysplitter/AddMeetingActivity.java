package com.example.moneysplitter;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneysplitter.data.DatabaseApp;
import com.example.moneysplitter.data.MeetingEntity;

public class AddMeetingActivity extends AppCompatActivity {
    private static final String TAG = "AddMeetingActivity";

    private DatabaseApp database;
    private String name, days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        database = DatabaseApp.getInstance(this);

        //Adding meeting to the list in MainActivity
        ((Button) findViewById(R.id.add_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addMeetingToDatabase();

                int addedMeetingId = database.meetingDao().getId(name, Integer.parseInt(days));
                Log.d(TAG, "addedMeetingId: " + addedMeetingId);

                Intent intent = new Intent(AddMeetingActivity.this, AddPersonActivity.class);
                if(addedMeetingId != 0) {
                    intent.putExtra("meetingId", addedMeetingId);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddMeetingActivity.this, R.string.error_info, Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    private void addMeetingToDatabase() {

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

        MeetingEntity meetingEntity = new MeetingEntity();
        meetingEntity.setName(name);
        meetingEntity.setDays(Integer.parseInt(days));
        database.meetingDao().insertAll(meetingEntity);

        Log.d(TAG, "addMeeting: added name - " + name + ", days - " + days);
    }
}