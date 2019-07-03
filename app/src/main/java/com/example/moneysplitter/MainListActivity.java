package com.example.moneysplitter;

import android.os.Bundle;

import com.example.moneysplitter.data.DatabaseApp;
import com.example.moneysplitter.data.MeetingEntity;
import com.example.moneysplitter.data.PersonEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainListActivity extends AppCompatActivity {
    private static final String TAG = "MainListActivity";//todo wyśletlać tylko osoby przypisane do spotkania

    private DatabaseApp database;
    private String meetingName;
    private MeetingEntity meeting = null;
    private MainListRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get value from intent
        meetingName = getIntent().getStringExtra("meetingName");

        database = DatabaseApp.getInstance(this);

        showSummaryInformations();
        showPersonsList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void showPersonsList() {
        List<PersonEntity> persons = database.personDao().getPersonsList(meeting.getUid());
        Log.d(TAG, "showPersonsList: " + persons.toString());

        recyclerView = (RecyclerView) findViewById(R.id.main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainListRecyclerViewAdapter(this, persons);
        recyclerView.setAdapter(adapter);
    }

    private void showSummaryInformations() {
        TextView meetingNameInSummary = findViewById(R.id.meeting_name_in_summary);
        TextView daysInSummary = findViewById(R.id.days_in_summary);

        meeting = database.meetingDao().getItem(meetingName);
        String days = String.valueOf(meeting.getDays());

        String daysText;
        if(days.equals("1")) {
            daysText = " dzień";
        } else {
            daysText = " dni";
        }

        meetingNameInSummary.setText(meetingName);
        daysInSummary.setText(", " + days + daysText);
    }
}