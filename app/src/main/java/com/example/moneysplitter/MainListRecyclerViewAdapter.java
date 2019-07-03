package com.example.moneysplitter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneysplitter.data.PersonEntity;

import java.util.List;

public class MainListRecyclerViewAdapter extends RecyclerView.Adapter<MainListRecyclerViewAdapter.PersonViewHolder> {
    private static final String TAG = "MainListRecyclerViewAda";
    private List<PersonEntity> personsList;
    private Context context;
// todo powtarzające się imiona

    public MainListRecyclerViewAdapter(Context context, List<PersonEntity> personsList) {
        Log.d(TAG, "MainListCursorRecyclerViewAdapter: constructor called");
        this.context = context;
        this.personsList = personsList;
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MainListViewHolder";

        TextView name;
        TextView days;
//        TextView value;

        private PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "PersonViewHolder: constructor called");

            this.name = (TextView) itemView.findViewById(R.id.person_name_main_list);
            this.days = (TextView) itemView.findViewById(R.id.person_days_main_list);
//            this.value = (TextView) itemView.findViewById(R.id.person_value_main_list);
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
        Log.d(TAG, "onBindViewHolder: starts" + i);

        if(personsList == null || personsList.size() == 0) {
//            holder.name.setText(R.string.no_persons_info);
//            holder.days.setVisibility(View.GONE);
//            holder.value.setVisibility(View.GONE);
        } else {
            PersonEntity person = personsList.get(i);
            Log.d(TAG, "onBindViewHolder: " + person.getUid() + " --> " + i);

            String days = String.valueOf(person.getDays());

            String daysText;
            if(days.equals("1")) {
                daysText = " dzień";
            } else {
                daysText = " dni";
            }

            holder.name.setText(person.getName());
            holder.days.setText(", " + days + daysText);
        }
    }

    @Override
    public int getItemCount() {
        return (personsList == null || personsList.size() == 0) ? 0 : personsList.size();
        // 1, because of single ViewHolder with info
    }
}
