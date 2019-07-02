package com.example.moneysplitter;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneysplitter.data.DatabaseApp;
import com.example.moneysplitter.data.GoalEntity;

public class AddGoalActivity extends AppCompatActivity {
    private static final String TAG = "AddGoalActivity";

    private DatabaseApp database;
    private int meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        database = DatabaseApp.getInstance(this);

        //get values from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);

        //click on add button
        ((Button) findViewById(R.id.add_btn3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addGoalToDatabase();

                Intent intent = new Intent(AddGoalActivity.this, AddGoalsListActivity.class);
                intent.putExtra("meetingId", meetingId);
                startActivity(intent);

                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    void addGoalToDatabase() {
        TextView nameView = findViewById(R.id.for_what_name);

        String name = String.valueOf(nameView.getText());
        int[] personsIds = database.personDao().getIds(meetingId);

        if(!name.equals("")) {
            for(int id : personsIds) {
                GoalEntity goal = new GoalEntity();
                goal.setTitle(name);
                goal.setConcern(true);
                goal.setPersonId(id);
                database.goalDao().insertAll(goal);
            }
        } else {
            Toast.makeText(AddGoalActivity.this, "Wpisz za co płacicie", Toast.LENGTH_LONG).show();
        }
    }

}
