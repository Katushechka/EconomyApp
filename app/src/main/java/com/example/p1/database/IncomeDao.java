package com.example.p1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.p1.database.Income;

import java.util.List;

@Dao
public interface IncomeDao {

    @Insert
    void insert(Income income);

    @Update
    void update(Income income);

    @Delete
    void delete(Income income);

    @Query("DELETE FROM income")
    void deleteAllIncomes();

    @Query("DELETE FROM income")
    void deleteAllNotes();

    @Query("SELECT * FROM income")
    LiveData<List<Income>> getAllIncomes();
}
