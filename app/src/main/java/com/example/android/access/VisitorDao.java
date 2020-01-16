package com.example.android.access;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VisitorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Visitor visitor);

    @Delete
    void delete(Visitor visitor);

    @Query("DELETE FROM visitors_table")
    void deleteAll();

    @Query("SELECT * FROM visitors_table ORDER BY Id ASC")
    LiveData<List<Visitor>> getAllVisitors();

}
