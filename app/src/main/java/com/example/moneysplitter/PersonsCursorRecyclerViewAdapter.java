package com.example.moneysplitter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonsCursorRecyclerViewAdapter extends RecyclerView.Adapter<PersonsCursorRecyclerViewAdapter.PersonViewHolder> {
    private static final String TAG = "MainListCursorRecyclerV";
    private Cursor cursor;

    public PersonsCursorRecyclerViewAdapter(Cursor cursor) {
        Log.d(TAG, "MainListCursorRecyclerViewAdapter: constructor called");
        this.cursor = cursor;
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MainListViewHolder";

        TextView name;
        TextView days;
        TextView value;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "PersonViewHolder: constructor called");

            this.name = (TextView) itemView.findViewById(R.id.person_name_main_list);
            this.days = (TextView) itemView.findViewById(R.id.person_days_main_list);
            this.value = (TextView) itemView.findViewById(R.id.person_value_main_list);
        }
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: starts");
        return new PersonViewHolder( LayoutInflater
                                    .from(viewGroup.getContext())
                                    .inflate(R.layout.content_main_list_detail, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int i) {
        Log.d(TAG, "onBindViewHolder: starts");

        if(cursor == null || cursor.getCount() == 0) {
            holder.name.setText(R.string.no_persons_info);
            holder.days.setVisibility(View.GONE);
            holder.value.setVisibility(View.GONE);
        } else {
            if(!cursor.moveToPosition(i)) {
                throw new IllegalStateException("Couldn't move to position " + i);
            }

            String name = cursor.getString(cursor.getColumnIndex(PersonTable.Column.NAME));
            String days = cursor.getString(cursor.getColumnIndex(PersonTable.Column.DAYS));
//            String view = cursor.getString(cursor.getColumnIndex(PersonTable.Column. ));

            holder.name.setText(name);
            holder.days.setText(days);
//            holder.value.setText(value);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: starts");
        if(cursor == null || cursor.getCount() == 0) {
            return 1; // 1, because of single ViewHolder with info
        }
        return cursor.getCount();
    }

    //return the old cursor or null
    Cursor swapCursor(Cursor newCursor) {
        if(newCursor == cursor) {
            return null;
        }

        final Cursor oldCursor = cursor;
        cursor = newCursor;
        if(newCursor != null) {
            //notify observers about the new cursor
            notifyDataSetChanged();
        } else {
            //notify the observers about lack of data
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }
}
