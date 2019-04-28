package com.example.moneysplitter;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddForWhatListActivity extends AppCompatActivity {
    private static final String TAG = "AddForWhatListActivity";

    private ListView forWhatNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_for_what_list);

        Intent intent = getIntent();
        final ArrayList<Integer> personsIds = intent.getIntegerArrayListExtra("personsIds");
        Log.d(TAG, "onCreate: personsIds: " + personsIds.toString());

        forWhatNames = (ListView) findViewById(R.id.for_what_names);

        //pobranie referencji do bazy
//        AppDatabase appDatabase = AppDatabase.getInstance(this);
        final SQLiteOpenHelper dbHelper = AppDatabase.getInstance(this);

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ((AppDatabase) dbHelper).deleteForWhatNamesView(db);
            ((AppDatabase) dbHelper).createForWhatNamesView(db);

            db = dbHelper.getReadableDatabase();
            String sql = "SELECT NAME FROM ForWhat GROUP BY NAME";
            Cursor cursor = db.query("ForWhatNames",
                    new String[] {ForWhat.Column.NAME},
                    null,
                    null,
                    null,
                    null,
                    null);

            if(cursor != null) {
                List<String> names = new ArrayList<>();
                if(cursor.moveToFirst()) {
                    do {
                        Log.d(TAG, "onCreate: " + cursor.getColumnIndex(ForWhat.Column.NAME));
                        names.add(cursor.getString(cursor.getColumnIndex(ForWhat.Column.NAME)));
                    } while (cursor.moveToNext());
                }
                if(!names.isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            AddForWhatListActivity.this,
                            R.layout.add_for_what_detail,
                            R.id.for_what_name_detail,
                            names);
                    forWhatNames.setAdapter(adapter);
                } else {
                    Log.d(TAG, "onCreate: table is empty");
                }
            }
            cursor.close();

        } catch(SQLException e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        ((Button) findViewById(R.id.add_for_what_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(AddForWhatListActivity.this, AddForWhatActivity.class);
                newIntent.putIntegerArrayListExtra("personsIds", personsIds);
                startActivity(newIntent);
            }
        });

        ((Button) findViewById(R.id.next2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(AddForWhatListActivity.this, MainActivity.class);
                startActivity(newIntent);
            }
        });

        Log.d(TAG, "onCreate: ends");
    }
}
