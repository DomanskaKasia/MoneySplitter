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
        final AppDatabase dbHelper = AppDatabase.getInstance(this);
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            ((Button) findViewById(R.id.add_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: starts");
                    TextView nameView = (TextView) findViewById(R.id.meeting_name);
                    TextView daysView = (TextView) findViewById(R.id.meeting_days);

                    String name = String.valueOf(nameView.getText());
                    String days = String.valueOf(daysView.getText());

                    if(days.equals(""))
                        days = "1";

                    if(!name.equals("")) {
                        dbHelper.insertMeeting(db, name, Integer.parseInt(days));
                        db.close();

                        Intent intent = new Intent(AddMeetingActivity.this, AddPersonActivity.class);
                        intent.putExtra("meetingName", name);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddMeetingActivity.this, "Wpisz nazwę spotkania", Toast.LENGTH_LONG).show();
                    }

                    Log.d(TAG, "onClick: ends");
                }
            });

        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "onCreate: ends");
    }
}
