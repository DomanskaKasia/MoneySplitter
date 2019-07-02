package com.example.moneysplitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.moneysplitter.data.DatabaseApp;

import java.util.List;

public class AddGoalsListActivity extends AppCompatActivity {
    private static final String TAG = "AddGoalsListActivity";

    private DatabaseApp database;
    private int meetingId;
    private ListView goalsTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goals_list);

        database = DatabaseApp.getInstance(this);

        //get values from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);

        goalsTitles = (ListView) findViewById(R.id.for_what_names);

        if(database != null) {
            List<String> titles = database.goalPersonMeetingDao().getGoalsTitles(meetingId);

            //show list of names
                if(!titles.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            AddGoalsListActivity.this,
                            R.layout.add_for_what_detail,
                            R.id.for_what_name_detail,
                            titles);
                goalsTitles.setAdapter(adapter);
            } else {
                Log.d(TAG, "onCreate: table is empty");
            }
        }

        ((Button) findViewById(R.id.add_for_what_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoalsListActivity.this, AddGoalActivity.class);
                intent.putExtra("meetingId", meetingId);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.next2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(AddGoalsListActivity.this, MainActivity.class) );
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}
