package com.example.p1;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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



    //från Internet
    public static synchronized EconomyRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    EconomyRoomDatabase.class, "economy_database")
                    .build();
        }
        return INSTANCE;
    }
/*    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };*/

  /*  private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private IncomeDao incomeDao;

        private PopulateDbAsyncTask(EconomyRoomDatabase db) {
            incomeDao = db.incomeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            incomeDao.insert(new Income("Title 1", 1, "lön", "12345"));
            incomeDao.insert(new Income("Title 1", 1, "lön", "12345"));
            incomeDao.insert(new Income("Title 1", 1, "lön", "12345"));
            return null;
        }
    }*/
}
