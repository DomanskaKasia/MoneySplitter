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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddForWhatListActivity extends AppCompatActivity {
    private static final String TAG = "AddForWhatListActivity";

    private int meetingId;
    private ListView forWhatNames;
    private ArrayList<Integer> personsIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_for_what_list);

        //get values from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);
        personsIds = getIntent().getIntegerArrayListExtra("personsIds");
        Log.d(TAG, "onCreate: personsIds: " + personsIds.toString());

        forWhatNames = (ListView) findViewById(R.id.for_what_names);

        //get cursor of names from joined table
        Cursor cursor = getContentResolver().query(ForWhatFromMeeting.CONTENT_URI,
                new String[] {ForWhatTable.PrefixColumn.NAME},
                MeetingTable.PrefixColumn._ID + " = ?",
                new String[] {String.valueOf(meetingId)},
                null,
                null);

            if(cursor != null) {
                List<String> names = new ArrayList<>();

                //make list of names from joined table
                if(cursor.moveToFirst()) {
                    do {
                        Log.d(TAG, "onCreate: " + cursor.getColumnIndex(ForWhatTable.Column.NAME));
                        names.add(cursor.getString(cursor.getColumnIndex(ForWhatTable.Column.NAME)));
                    } while (cursor.moveToNext());
                }

                //show list of names
                if(!names.isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            AddForWhatListActivity.this,
                            R.layout.add_for_what_detail,
                            R.id.for_what_name_detail,
                            names);
                    forWhatNames.setAdapter(adapter);
                } else {
                    Log.d(TAG, "onCreate: table is empty");
                }
            }

        ((Button) findViewById(R.id.add_for_what_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddForWhatListActivity.this, AddForWhatActivity.class);
                intent.putExtra("meetingId", meetingId);
                intent.putIntegerArrayListExtra("personsIds", personsIds);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.next2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(AddForWhatListActivity.this, MainActivity.class) );
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}
