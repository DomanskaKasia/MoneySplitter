package com.example.moneysplitter;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ListView meetingNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingNames = (ListView) findViewById(R.id.meeting_names);

        //pobranie referencji do bazy
        AppDatabase appDatabase = AppDatabase.getInstance(this);

        try {
            final SQLiteDatabase db = appDatabase.getReadableDatabase();
            Cursor cursor = db.query(Meeting.TABLE_NAME,
                    new String[] {Meeting.Column.NAME},
                    null,
                    null,
                    null,
                    null,
                    null);

            if(cursor != null) {
                List<String> names = new ArrayList<>();
                if(cursor.moveToFirst()) {
                    do {
                        Log.d(TAG, "onCreate: " + cursor.getColumnIndex(Meeting.Column.NAME));
                        names.add(cursor.getString(cursor.getColumnIndex(Meeting.Column.NAME)));
                    } while (cursor.moveToNext());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        R.layout.add_meeting_detail,
                        R.id.meeting_name,
                        names);
                meetingNames.setAdapter(adapter);
            }
        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        Button btn = findViewById(R.id.add_meeting_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}
