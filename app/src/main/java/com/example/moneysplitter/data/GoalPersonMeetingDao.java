package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GoalPersonMeetingDao {

    @Query("SELECT Goals.title FROM Goals " +
            "INNER JOIN Persons ON Goals.personId = Persons.uid " +
            "INNER JOIN Meetings ON Persons.meetingId = Meetings.uid " +
            "WHERE Meetings.uid = (:meetingId)")
    List<String> getGoalsTitles(int meetingId);
}
