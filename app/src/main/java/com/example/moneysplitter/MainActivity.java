package com.example.moneysplitter;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

        //download reference to the database
        final AppDatabase dbHelper = AppDatabase.getInstance(this);

        try {
            final SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MeetingTable.TABLE_NAME,
                    new String[] {MeetingTable.Column.NAME},
                    null,
                    null,
                    null,
                    null,
                    null);

            if(cursor != null) {
                List<String> names = new ArrayList<>();
                if(cursor.moveToFirst()) {
                    do {
                        Log.d(TAG, "onCreate: " + cursor.getColumnIndex(MeetingTable.Column.NAME));
                        names.add(cursor.getString(cursor.getColumnIndex(MeetingTable.Column.NAME)));
                    } while (cursor.moveToNext());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this,
                        R.layout.add_meeting_detail,
                        R.id.meeting_name,
                        names);
                meetingNames.setAdapter(adapter);
            }
            cursor.close();
            db.close();

        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        //Clicking on item in the list
        meetingNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: " + item);

                startActivity( new Intent(MainActivity.this, MainListActivity.class) );
            }
        });

        //Clicking on floating button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: floating action button clicked");
                startActivity(new Intent(MainActivity.this, AddMeetingActivity.class));
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}