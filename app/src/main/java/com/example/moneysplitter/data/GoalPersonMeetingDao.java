package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GoalPersonMeetingDao {

    @Query("SELECT DISTINCT Goals.title FROM Goals " +
            "INNER JOIN Persons ON Goals.personId = Persons.uid " +
            "INNER JOIN Meetings ON Persons.meetingId = Meetings.uid " +
            "WHERE Meetings.uid = (:meetingId)")
    List<String> getGoalsTitles(int meetingId);

    @Query("SELECT Goals.title FROM Goals " +
            "INNER JOIN Persons ON Goals.personId = Persons.uid " +
            "INNER JOIN Meetings ON Persons.meetingId = Meetings.uid " +
            "WHERE Meetings.uid = (:meetingId) AND Persons.uid = (:personId) AND Goals.concern = 1")
    List<String> getGoalsTitles(int meetingId, int personId);
}