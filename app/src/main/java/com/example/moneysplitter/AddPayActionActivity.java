package com.example.moneysplitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moneysplitter.data.DatabaseApp;
import com.example.moneysplitter.data.PayActionEntity;
import com.example.moneysplitter.data.PersonEntity;

import java.util.List;

public class AddPayActionActivity extends AppCompatActivity {
    private static final String TAG = "AddPayActionActivity";

    private DatabaseApp database;
    private int meetingId, personId, goalId;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView personsText, goalsText;
    private List<String> persons;
    private String choosenPerson;
    private Spinner personsSpinner, goalsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pay_action);
        //todo zoptymalizowac pobieranie danych do list

        //get value from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);

        database = DatabaseApp.getInstance(this);



        loadPersonsSpinner();
        choosenPerson = String.valueOf(personsSpinner.getSelectedItem());
        loadGoalsSpinner();

        //slick on add button
        ((Button) findViewById(R.id.add_payment_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PayActionEntity payment = new PayActionEntity();
//                payment.setValue(personId);
//                payment.setGoal_id();
//                database.payActionDao().insertAll(payment);
            }
        });
        Log.d(TAG, "onCreate: ends");
    }



    public void checkPayButton(View v) {
        Log.d(TAG, "checkPayButton: starts");
        radioGroup = findViewById(R.id.radioGoalsGroup);
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioId);

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.pay_radio_button:
                loadPersonsSpinner();
                choosenPerson = String.valueOf(personsSpinner.getSelectedItem());
                loadGoalsSpinner();
                break;
            case R.id.give_back_radio_button:
                personsSpinner.setVisibility(View.GONE);
                personsText.setVisibility(View.GONE);
                break;
            default:

        }
//        Log.d(TAG, "checkPayButton: " + radioButton.getText());
    }

    private void loadPersonsSpinner() {
        Log.d(TAG, "loadPersonsSpinner: starts");
        personsSpinner = findViewById(R.id.persons_spinner);
        personsText = findViewById(R.id.persons_spinner_text);

        personsSpinner.setVisibility(View.VISIBLE);
        personsText.setVisibility(View.VISIBLE);

        if(persons == null || persons.size() == 0){
            persons = database.personDao().getNames(meetingId);
        }

        if(persons.size() > 0) {
            ArrayAdapter<String> personsAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    persons);
            personsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            personsSpinner.setAdapter(personsAdapter);
        }

    }

    private void loadGoalsSpinner() {
        Log.d(TAG, "loadGoalsSpinner: starts");
        ((TextView) findViewById(R.id.goals_spinner_text)).setVisibility(View.VISIBLE);

        goalsSpinner = findViewById(R.id.goals_spinner);
        goalsSpinner.setVisibility(View.VISIBLE);

        personId = database.personDao().getId(meetingId, choosenPerson);
        List<String> goals = database.goalPersonMeetingDao().getGoalsTitles(meetingId, personId);

        ArrayAdapter<String> goalsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                goals);
        goalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        goalsSpinner.setAdapter(goalsAdapter);
    }
}
