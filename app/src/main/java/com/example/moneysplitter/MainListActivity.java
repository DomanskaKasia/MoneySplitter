package com.example.moneysplitter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.moneysplitter.data.PersonTable;

import java.security.InvalidParameterException;

public class MainListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainListActivity";//todo wyśletlać tylko osoby przypisane do spotkania

    public static final int LOADER_ID = 0;

    private PersonsCursorRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

//    LoadManager method
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.d(TAG, "onCreateLoader: called with " + i);
        String[] columns = {PersonTable.Column._ID, PersonTable.Column.NAME,
                PersonTable.Column.DAYS};
        if(i == LOADER_ID) {
            return new CursorLoader(this,
                    PersonTable.CONTENT_URI,
                    columns,
                    null,
                    null,
                    null);
        } else {
            throw new InvalidParameterException("Invalid loader i: " + i);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished: called");
        int count = -1;

        if(cursor != null) {
            while (cursor.moveToNext()) {
                for(int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + "; " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ------------------");
            }
            count = cursor.getCount();
        }
        Log.d(TAG, "onLoadFinished: count is " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: called");
        adapter.swapCursor(null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showPersonsList() {
        String[] projections = {PersonTable.Column._ID,
                PersonTable.Column.NAME,
                PersonTable.Column.DAYS};

        Cursor cursor = getContentResolver().query(PersonTable.CONTENT_URI,
                projections,
                null,
                null,
                PersonTable.Column._ID + " DESC");

        recyclerView = (RecyclerView) findViewById(R.id.main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonsCursorRecyclerViewAdapter(cursor);
        recyclerView.setAdapter(adapter);

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }
}
