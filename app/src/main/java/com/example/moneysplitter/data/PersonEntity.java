package com.example.moneysplitter.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Persons")
public class PersonEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    public String name;
    public int days;
    public int meetingId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }
}
