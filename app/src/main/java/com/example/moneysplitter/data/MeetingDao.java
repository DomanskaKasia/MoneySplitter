package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MeetingDao {

    @Query("SELECT * FROM Meetings")
    List<MeetingEntity> getAll();

//    @Query("SELECT * FROM Meetings WHERE uid (:ids)")
//    List<MeetingEntity> loadByIds(int[] ids);

    @Query("SELECT uid FROM Meetings WHERE name = (:name) AND days = (:days)")
    int getId(String name, int days);
//
    @Insert
    void insertAll(MeetingEntity... meeting);
//
//    @Query("UPDATE Meetings SET name = (:name) AND days = (:days) WHERE uId = (:id)")
//    void changeMeetingById(int id, String name, int days);
//
//    @Delete()
//    void delete(MeetingEntity meeting);
//
//    @Query("DELETE FROM Meetings WHERE uID = (:id)")
//    void deleteById(int id);
}
