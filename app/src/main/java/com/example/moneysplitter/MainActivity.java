package com.example.moneysplitter;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneysplitter.data.DatabaseApp;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";//todo niepowtarzające się nazwy

    private DatabaseApp database;
    private ListView meetingNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = DatabaseApp.getInstance(this);
        showMeetingsList();

//        Click on item in the list
        meetingNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String meetingName = parent.getItemAtPosition(position).toString();
//                Log.d(TAG, "onItemClick: " + meetingName);
//
//                Intent intent = new Intent(MainActivity.this, MainListActivity.class);
//                intent.putExtra("meetingName", meetingName);
//                startActivity(intent);
            }
        });

        //Click on floating button
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: floating action button clicked");
                startActivity(new Intent(MainActivity.this, AddMeetingActivity.class));
            }
        });

        Log.d(TAG, "onCreate: ends");
    }



    private void showMeetingsList() {
        meetingNames = (ListView) findViewById(R.id.meeting_names);

        if(database != null) {
            List<String> names = database.meetingDao().getNames();
            if(names.size() > 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this,
                        R.layout.add_meeting_detail,
                        R.id.meeting_name,
                        names);
                meetingNames.setAdapter(adapter);
            } else {
                TextView noMeetingListMessage = findViewById(R.id.no_meeting_list_info);
                noMeetingListMessage.setText(R.string.no_meeting_list_info);
                noMeetingListMessage.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }
    }
}