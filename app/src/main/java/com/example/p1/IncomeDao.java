package com.example.p1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IncomeDao {

    @Insert
    void insert(Income income);

    @Update
    void update(Income income);

    @Delete
    void delete(Income income);

    //Query ("DELETE FROM income WHERE incomeTitle =:title")
    //void deleteIncome(String titel);

    @Query("DELETE FROM income")
    void deleteAllIncomes();

    @Query("DELETE FROM income")
    void deleteAllNotes();

    @Query("SELECT * FROM income")
    LiveData<List<Income>> getAllIncomes();
}
