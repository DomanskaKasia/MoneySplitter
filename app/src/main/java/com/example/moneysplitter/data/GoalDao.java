package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GoalDao {

    @Query("SELECT * FROM Goals")
    List<GoalEntity> getAll();

    @Query("SELECT title FROM Goals WHERE personId = (:personId)")
    List<String> getTitles(ArrayList<Integer> personId);

    @Insert
    void insertAll(GoalEntity... goal);
}
