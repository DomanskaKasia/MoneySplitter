package com.example.moneysplitter;

import android.content.ContentValues;
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

public class AddPersonActivity extends AppCompatActivity {
    private static final String TAG = "AddPersonActivity";

    private int meetingId;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);//todo sprawdzanie czy ilość dni nie jest większa od ilości dni spotkania

        //get value from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);

       //dodanie osoby do bazy
        ((Button) findViewById(R.id.add_btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addMeetingtoDatabase();
                int personId = getAddedPersonId();

                if(personId != 0) {
                    Intent intent = new Intent(AddPersonActivity.this, AddPersonListActivity.class);
                    intent.putExtra("personId", personId);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddPersonActivity.this, R.string.error_info, Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    private void addMeetingtoDatabase() {
        TextView nameView = (TextView) findViewById(R.id.person_name);
        TextView daysView = (TextView) findViewById(R.id.person_days);

        name = String.valueOf(nameView.getText());
        String days = String.valueOf(daysView.getText());

        if(days.equals(""))
            days = "1";

        if (name.length() == 0 || name.trim().equals("")) {
            Toast.makeText(AddPersonActivity.this, "Wpisz nazwę spotkania", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(PersonTable.Column.NAME, name);
        values.put(PersonTable.Column.DAYS, days);
        values.put(PersonTable.Column.ID_MEETING, meetingId);

        getContentResolver().insert(PersonTable.CONTENT_URI, values);
        Log.d(TAG, "addPerson: added name - " + name + ", days - " + days + ", meetingId - " + meetingId);
    }

    private int getAddedPersonId() {
        Cursor cursor = getContentResolver().query(PersonTable.CONTENT_URI,
                new String[] {PersonTable.Column._ID},
                MeetingTable.Column.NAME + " = ?",
                new String[] {name},
                null);

        if(cursor != null && cursor.moveToFirst()) {
            return Integer.parseInt(cursor.getString(0));
        }
        return 0;
    }
}
