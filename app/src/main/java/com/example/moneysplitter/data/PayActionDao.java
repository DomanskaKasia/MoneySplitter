package com.example.moneysplitter.data;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface PayActionDao {

    @Insert
    void insertAll(PayActionEntity... payment);
}
