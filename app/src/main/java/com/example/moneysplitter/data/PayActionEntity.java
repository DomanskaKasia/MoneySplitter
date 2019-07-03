package com.example.moneysplitter.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Payment_actions")
public class PayActionEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    public double value;
    public int goal_id;
}