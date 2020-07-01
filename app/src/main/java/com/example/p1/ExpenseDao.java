package com.example.p1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    //Query ("DELETE FROM income WHERE incomeTitle =:title")
    //void deleteIncome(String titel);

    @Query("DELETE FROM expense")
    void deleteAllIncomes();

    @Query("DELETE FROM expense")
    void deleteAllNotes();

    @Query("SELECT * FROM expense")
    LiveData<List<Expense>> getAllExpenses();
}
