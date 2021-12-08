package com.example.p1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Class EconomyRoomDatabase is responsible for creating and returning a new room database instance
 * och for provoding access to the DAO instance
 * @author Ekaterina K
 */


@Database(entities = {Income.class, Expense.class}, version = 1) //här skriver vi vilka Entity (tabeller) vi vill ha
//@Database(entities = {Expense.class}, version = 1)
public abstract class EconomyRoomDatabase extends RoomDatabase {

    private static EconomyRoomDatabase INSTANCE;
    public abstract IncomeDao incomeDao();
    public abstract ExpenseDao expenseDao();


    public static synchronized EconomyRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    EconomyRoomDatabase.class, "economy_database")
                    .build();
        }
        return INSTANCE;
    }
}
