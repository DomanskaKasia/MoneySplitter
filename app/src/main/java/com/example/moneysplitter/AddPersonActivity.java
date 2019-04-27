package com.example.moneysplitter;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {
    private static final String TAG = "AddPersonActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

//        //pobranie nazwy spotkania z intencji
//        String chosenName = (String)getIntent().getExtras().get();

        //pobranie referencji do bazy
        final SQLiteOpenHelper dbHelper = AppDatabase.getInstance(this);
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            ((Button) findViewById(R.id.add_btn2)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: starts");
                    TextView nameView = (TextView) findViewById(R.id.person_name);
                    TextView daysView = (TextView) findViewById(R.id.person_days);

                    String name = String.valueOf(nameView.getText());
                    String days = String.valueOf(daysView.getText());

                    if(days.equals(""))
                        days = "1";

                    if(!name.equals("")) {
                        ((AppDatabase) dbHelper).insertPerson(db, name, Integer.parseInt(days));

                        Intent intent = new Intent(AddPersonActivity.this, AddPersonListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AddPersonActivity.this, "Wpisz nazwę spotkania", Toast.LENGTH_LONG).show();
                    }
//
                    Log.d(TAG, "onClick: ends");
                }
            });

        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "onCreate: ends");
    }
}