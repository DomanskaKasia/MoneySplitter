package com.example.moneysplitter;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddForWhatActivity extends AppCompatActivity {
    private static final String TAG = "AddForWhatActivity";

    private int meetingId;
    private ArrayList<Integer> personsIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_for_what);

        //get values from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);
        personsIds = getIntent().getIntegerArrayListExtra("personsIds");
        Log.d(TAG, "onCreate: personsIds: " + personsIds.toString());

        //click on add button
        ((Button) findViewById(R.id.add_btn3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addForWhatToDatabase();

                Intent intent = new Intent(AddForWhatActivity.this, AddForWhatListActivity.class);
                intent.putExtra("meetingId", meetingId);
                intent.putIntegerArrayListExtra("personsIds", personsIds);
                startActivity(intent);

                Log.d(TAG, "onClick: ends");
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    void addForWhatToDatabase() {
        TextView nameView = (TextView) findViewById(R.id.for_what_name);

        String name = String.valueOf(nameView.getText());

        ContentValues values;

        if(!name.equals("")) {
            for(int id : personsIds) {
                values = new ContentValues();
                values.put(ForWhatTable.Column.NAME, name);
                values.put(ForWhatTable.Column.CONCERN, 1);
                values.put(ForWhatTable.Column.ID_PERSON, id);

                getContentResolver().insert(ForWhatTable.CONTENT_URI, values);
            }
        } else {
            Toast.makeText(AddForWhatActivity.this, "Wpisz za co płacicie", Toast.LENGTH_LONG).show();
        }
    }

}
