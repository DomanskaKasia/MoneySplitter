package com.example.moneysplitter;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneysplitter.data.DatabaseApp;
import com.example.moneysplitter.data.PersonEntity;

public class AddPersonActivity extends AppCompatActivity {
    private static final String TAG = "AddPersonActivity";

    private DatabaseApp database;
    private int meetingId;
    private String name, days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        //todo sprawdzanie czy ilość dni nie jest większa od ilości dni spotkania
        //todo dodać możliwość dodawania osób z konkaktów

        database = DatabaseApp.getInstance(this);

        //get value from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);

       //click on add button
        ((Button) findViewById(R.id.add_btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starts");

                addMeetingToDatabase();
                int personId = database.personDao().getId(name, Integer.parseInt(days));

                if(personId != 0) {
                    Intent intent = new Intent(AddPersonActivity.this, AddPersonListActivity.class);
                    intent.putExtra("meetingId", meetingId);
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



    private void addMeetingToDatabase() {
        TextView nameView = findViewById(R.id.person_name);
        TextView daysView = findViewById(R.id.person_days);

        name = String.valueOf(nameView.getText());
        days = String.valueOf(daysView.getText());

        if(days.equals(""))
            days = "1";

        if (name.length() == 0 || name.trim().equals("")) {
            Toast.makeText(AddPersonActivity.this, "Wpisz nazwę spotkania", Toast.LENGTH_LONG).show();
            return;
        }

        PersonEntity person = new PersonEntity();
        person.setName(name);
        person.setDays(Integer.parseInt(days));
        person.setValue(0.0);
        person.setMeetingId(meetingId);
        database.personDao().insertAll(person);

        Log.d(TAG, "addPerson: added name - " + name + ", days - " + days + ", meetingId - " + meetingId);
    }
}
