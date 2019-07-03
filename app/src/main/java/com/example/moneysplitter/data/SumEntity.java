package com.example.moneysplitter.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Payment_sums")
public class SumEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    public double value;
    public int person_id;
    public int goal_id;
}
