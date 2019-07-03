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

import com.example.moneysplitter.data.DatabaseApp;

import java.util.List;

public class AddPayActionActivity extends AppCompatActivity {
    private static final String TAG = "AddPayActionActivity";

    private DatabaseApp database;
    private int meetingId;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Spinner personsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pay_action);

        //get value from intent
        meetingId = getIntent().getIntExtra("meetingId", 0);

        database = DatabaseApp.getInstance(this);

        radioGroup = findViewById(R.id.radioGoalsGroup);

        loadPersonsSpinner();

        //slick on add button
        ((Button) findViewById(R.id.add_payment_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);
                Log.d(TAG, "chceckButton: " + radioButton.getText());
            }
        });
    }

    public void checkPayButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
//        switch (radio
        Log.d(TAG, "chceckButton: " + radioButton.getText());
    }

    private void loadPersonsSpinner() {
        personsSpinner = findViewById(R.id.persons_spinner);

        List<String> persons = database.personDao().getNames(meetingId);

        ArrayAdapter<String> personsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                persons);
        personsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        personsSpinner.setAdapter(personsAdapter);
    }
}
