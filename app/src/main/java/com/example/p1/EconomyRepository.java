package com.example.p1;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The IncomeRepository class is responsible for interacting with the Room database on behalf of the
 * ViewModel and provides methods that use the DAO to insert, delete and
 *  query income records.
 * @author Ekaterina K
 */
public class EconomyRepository {

    private ExpenseDao expenseDao;
    private IncomeDao incomeDao;
    private LiveData<List<Income>> allIncomes;
    private LiveData<List<Expense>> allExpenses;



    public EconomyRepository(Application application) {
        EconomyRoomDatabase database;
        database = EconomyRoomDatabase.getInstance(application);
        incomeDao = database.incomeDao();
        allIncomes = incomeDao.getAllIncomes();
        expenseDao = database.expenseDao();
        allExpenses = expenseDao.getAllExpenses();


    }
    //---------------------------------------------------------------------------------------------
//     INCOMES
    //-------------------------------------------------------------------------------------------

    public void insert(Income income) {                             //ROOM ger inte att lägga till, ta bort   i MAIN THREAD, därför görs det InBackground
        new InsertIncomeAsyncTask(incomeDao).execute(income);
        /*InsertIncomeAsyncTask insertIncomeAsyncTask = new InsertIncomeAsyncTask(incomeDao);
        insertIncomeAsyncTask.execute(income);*/
    }

    private static class InsertIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao incomeDao;

        private InsertIncomeAsyncTask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.insert(incomes[0]);
            return null;
        }
    }




    public void delete(Income income) {
        DeleteIncomeAsyncTask deleteIncomeAsyncTask = new DeleteIncomeAsyncTask(incomeDao);
        deleteIncomeAsyncTask.execute(income);
    }

    private static class DeleteIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao incomeDao;

        private DeleteIncomeAsyncTask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.delete(incomes[0]);
            return null;
        }
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(incomeDao).execute();
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private IncomeDao incomeDao;

        private DeleteAllNotesAsyncTask(IncomeDao noteDao) {
            this.incomeDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            incomeDao.deleteAllNotes();
            return null;
        }
    }




    public void update(Income income) {
        UpdateIncomeAsyncTask updateIncomeAsyncTask = new UpdateIncomeAsyncTask(incomeDao);
        updateIncomeAsyncTask.execute(income);
    }

    private static class UpdateIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao incomeDao;

        private UpdateIncomeAsyncTask(IncomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDao.update(incomes[0]);
            return null;
        }
    }


    public LiveData<List<Income>> getAllIncomes() {
        return allIncomes;
    }




    //---------------------------------------------------------------------------------------------
    //EXPENSES
    //-----------------------------------------------------------------------------------------

    public void insert(Expense expense) {                             //ROOM ger inte att lägga till, ta bort   i MAIN THREAD, därför görs det InBackground
        new InsertExpenseAsyncTask(expenseDao).execute(expense);

    }

    private static class InsertExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;

        private InsertExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.insert(expenses[0]);
            return null;
        }
    }




    public void delete(Expense expense) {                             //ROOM ger inte att lägga till, ta bort   i MAIN THREAD, därför görs det InBackground
        new DeleteExpenseAsyncTask(expenseDao).execute(expense);

    }

    private static class DeleteExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;

        private DeleteExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.delete(expenses[0]);
            return null;
        }
    }



    public void deleteAllExpenses() {
        new DeleteAllExpensesAsyncTask(expenseDao).execute();
    }

    private static class DeleteAllExpensesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpenseDao expenseDao;

        private DeleteAllExpensesAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            expenseDao.deleteAllNotes();
            return null;
        }
    }



    public void update(Expense expense) {                             //ROOM ger inte att lägga till, ta bort   i MAIN THREAD, därför görs det InBackground
        new UpdateExpenseAsyncTask(expenseDao).execute(expense);

    }

    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDao expenseDao;

        private UpdateExpenseAsyncTask(ExpenseDao expenseDao) {
            this.expenseDao = expenseDao;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDao.update(expenses[0]);
            return null;
        }
    }



    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }



}
