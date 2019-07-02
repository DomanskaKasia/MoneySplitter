package com.example.moneysplitter.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Goals")
public class GoalEntity {

    @PrimaryKey (autoGenerate = true)
    private int uid;
    public String title;
    public boolean concern;
    public int personId;

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isConcern() {
        return concern;
    }

    public void setConcern(boolean concern) {
        this.concern = concern;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
