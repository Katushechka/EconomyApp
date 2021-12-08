package com.example.p1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.p1.database.EconomyRepository;
import com.example.p1.database.Expense;
import com.example.p1.database.Income;

import java.util.List;

/**
 * ViewModel is responsible for creating an instance of the repository and for providing
 * methods and LiveData objects that can be utilized by the UI controller to keep UI synchronized
 * with the underlying database
 * @author Ekaterina K
 *  */

public class EconomyViewModel extends AndroidViewModel {
    private EconomyRepository repository;
    private LiveData<List<Income>> allIncomes;
    private LiveData<List<Expense>> allExpenses;

    public EconomyViewModel(@NonNull Application application) {
        super(application);

        repository = new EconomyRepository(application);
        allIncomes = repository.getAllIncomes();
        allExpenses = repository.getAllExpenses();
    }

    public void insert(Income income) {
        repository.insert(income);
    }
    public void insertExpense(Expense expense) {
        repository.insert(expense);
    }



    public void update(Income income) {
        repository.update(income);
    }
    public void updateExpense(Expense expense) {
        repository.update(expense);
    }


    public void delete(Income income) {
        repository.delete(income);
    }

    public void deleteExpense(Expense expense) {
        repository.delete(expense);
    }


    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public void deleteAllExpenses() {
        repository.deleteAllExpenses();
    }

//returnarar lista efter att den var ändrat
    public LiveData<List<Income>> getAllNotes() {
        return allIncomes;
    }
    public LiveData<List<Expense>> getAllExpenses() {
        return allExpenses;
    }

}
