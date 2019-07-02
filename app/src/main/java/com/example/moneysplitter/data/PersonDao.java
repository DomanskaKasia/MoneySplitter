package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM Persons")
    List<PersonEntity> getAll();

    @Query("SELECT name FROM Persons WHERE meetingId = (:meetingId)")
    List<String> getNames(int meetingId);

    @Query("SELECT uid FROM Persons WHERE name = (:name) AND days = (:days)")
    int getId(String name, int days);

    @Insert
    void insertAll(PersonEntity... person);
}
