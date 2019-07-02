package com.example.moneysplitter;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moneysplitter.data.DatabaseApp;

import java.util.ArrayList;
import java.util.List;

public class AddPersonListActivity extends AppCompatActivity {
    private static final String TAG = "AddPersonListActivity";

    private DatabaseApp database;
    private int meetingId;
    private int personId;

    private ListView personsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person_list);

        database = DatabaseApp.getInstance(this);

        //get values from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);
        personId = getIntent().getIntExtra("personId", 0);

        personsNames = (ListView) findViewById(R.id.persons_names);

        final ArrayList<Integer> personsIds = new ArrayList<>();

        if(database != null) {
            List<String> names = database.personDao().getNames(meetingId);

            //show list of persons
            if(!names.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
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
                int[] personsIds = database.personDao().getIds(meetingId);
                Intent intent = new Intent(AddPersonListActivity.this, AddGoalActivity.class);
                intent.putExtra("meetingId", meetingId);
                intent.putExtra("personsIds", personsIds);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}
