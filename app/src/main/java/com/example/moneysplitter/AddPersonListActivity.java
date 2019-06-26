package com.example.moneysplitter;

import android.content.Intent;
import android.database.Cursor;
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

public class AddPersonListActivity extends AppCompatActivity {
    private static final String TAG = "AddPersonListActivity";

    private int meetingId;
    private int personId;

    private ListView personsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_list);

        //get values from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);
        personId = getIntent().getIntExtra("personId", 0);

        personsNames = (ListView) findViewById(R.id.persons_names);

        final ArrayList<Integer> personsIds = new ArrayList<>();

        Cursor cursor = getContentResolver().query(PersonTable.CONTENT_URI,
                new String[] {PersonTable.Column._ID, PersonTable.Column.NAME},
                PersonTable.Column.ID_MEETING + " = ?",
                new String[] {String.valueOf(meetingId)},
                null,
                null);

        if(cursor != null) {
            List<String> names = new ArrayList<>();

            if(cursor.moveToFirst()) {
                do {
                    Log.d(TAG, "onCreate: " + cursor.getColumnIndex(PersonTable.Column.NAME));
                    names.add(cursor.getString(cursor.getColumnIndex(PersonTable.Column.NAME)));
                    personsIds.add(cursor.getInt(cursor.getColumnIndex(PersonTable.Column._ID)));
                } while (cursor.moveToNext());
            }
            if(!names.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        AddPersonListActivity.this,
                        R.layout.add_person_list_detail,
                        R.id.person_name_list,
                        names);
                personsNames.setAdapter(adapter);
            } else {
                Log.d(TAG, "onCreate: table is empty");
            }
        } else {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        ((Button) findViewById(R.id.add_person_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPersonListActivity.this, AddPersonActivity.class);
                intent.putExtra("meetingId", meetingId);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPersonListActivity.this, AddForWhatActivity.class);
                intent.putIntegerArrayListExtra("personsIds", personsIds);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}
