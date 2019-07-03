package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM Persons")
    List<PersonEntity> getAll();

    @Query("SELECT * FROM Persons WHERE meetingId = (:meetingId)")
    List<PersonEntity> getPersonsList(int meetingId);

    @Query("SELECT uid FROM Persons WHERE meetingId = (:meetingId)")
    int[] getIds(int meetingId);

    @Query("SELECT name FROM Persons WHERE meetingId = (:meetingId)")
    List<String> getNames(int meetingId);

    @Query("SELECT uid FROM Persons WHERE meetingId = (:meetingId) AND name = (:name)")
    int getId(int meetingId, String name);

    @Insert
    void insertAll(PersonEntity... person);
}
