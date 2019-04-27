package com.example.moneysplitter;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddForWhatActivity extends AppCompatActivity {
    private static final String TAG = "AddForWhatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_for_what);

        //pobranie referencji do bazy
        final SQLiteOpenHelper dbHelper = AppDatabase.getInstance(this);
        try {
            final SQLiteDatabase db = dbHelper.getWritableDatabase();

            //dodanie na co się składają do bazy
            ((Button) findViewById(R.id.add_btn3)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: starts");
                    TextView nameView = (TextView) findViewById(R.id.for_what_name);

                    String name = String.valueOf(nameView.getText());

                    if(!name.equals("")) {
                        ((AppDatabase) dbHelper).insertForWhat(db, name);

                        Intent newIntent = new Intent(AddForWhatActivity.this, AddForWhatListActivity.class);
                        startActivity(newIntent);
                    } else {
                        Toast.makeText(AddForWhatActivity.this, "Wpisz za co płacicie", Toast.LENGTH_LONG).show();
                    }

                    Log.d(TAG, "onClick: ends");
                }
            });

        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "onCreate: ends");
    }

}
