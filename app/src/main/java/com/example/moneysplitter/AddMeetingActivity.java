package com.example.moneysplitter;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddMeetingActivity extends AppCompatActivity {
    private static final String TAG = "AddMeetingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        //pobranie id spotkania z intencji
//        int meetingId = (Integer)getIntent().getExtras().get(MEETING_ID);

        //pobranie referencji do bazy
        final SQLiteOpenHelper dbMeetingHelper = AppDatabase.getInstance(this);
        try {
            final SQLiteDatabase db = dbMeetingHelper.getWritableDatabase();

            ((Button) findViewById(R.id.add_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: starts");
                    TextView name = (TextView) findViewById(R.id.meeting_name);
                    TextView days = (TextView) findViewById(R.id.meeting_days);
                    ((AppDatabase) dbMeetingHelper).insertMeeting(db, String.valueOf(name.getText()),
                            Integer.parseInt(String.valueOf(days.getText())));

                    Intent intent = new Intent(AddMeetingActivity.this, AddForWhatActivity.class);
                    startActivity(intent);

                    Log.d(TAG, "onClick: ends");
                }
            });

        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "onCreate: ends");
    }
}
