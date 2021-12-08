package com.example.p1;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p1.database.Expense;
import com.example.p1.listAdapter.ExpenseListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowExpensesWithinDateinterval extends AppCompatActivity {
    private Dateinterval dateinterval;
    private EconomyViewModel economyViewModel;
    private static final String STARTDATE = "startDate";
    private static final String STOPDATE = "stopDate";
    private String startdate;
    private String stopdate;
    private LiveData<List<Expense>> allNotes;   //ta inte bort

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenselist_within_dateinterval);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ExpenseListAdapter adapter = new ExpenseListAdapter();
        recyclerView.setAdapter(adapter);

        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);
        LiveData<List<Expense>> allExpense = economyViewModel.getAllExpenses();
        allExpense.observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenses) {
                adapter.setExpenses(filterExpenseForDate(expenses));
            }
        });
         getDate();
    }

    public void getDate(){
        Intent intent = getIntent();
        startdate =intent.getStringExtra(STARTDATE);
        stopdate = intent.getStringExtra(STOPDATE);
    }

    private List<Expense> filterExpenseForDate (List<Expense> allExpense){
        List<Expense> filterList = new ArrayList<Expense>();
        String year;
        String month;
        String day;
        String yearStartDate;
        String monthStartDate;
        String dayStartDate;
        String yearStopDate;
        String monthStopDate;
        String dayStopDate;

        int firstSlashStartDate = startdate.indexOf('/');
        int lastSlashStartDate = startdate.length()-3;
        yearStartDate = "20"+startdate.substring(lastSlashStartDate+1, startdate.length());
        monthStartDate = startdate.substring(0, firstSlashStartDate);
        dayStartDate = startdate.substring(firstSlashStartDate+1, lastSlashStartDate);
        int firstSlashStopDate = stopdate.indexOf('/');
        int lastSlashStopDate = stopdate.length()-3;
        yearStopDate = "20"+stopdate.substring(lastSlashStopDate+1, startdate.length());
        monthStopDate = stopdate.substring(0, firstSlashStopDate);
        dayStopDate = stopdate.substring(firstSlashStopDate+1, lastSlashStopDate);

        int yearStartD = Integer.parseInt(yearStartDate);
        int monthStartD = Integer.parseInt(monthStartDate);
        int dayStartD = Integer.parseInt(dayStartDate);

        int yearStopD = Integer.parseInt(yearStopDate);
        int monthStopD = Integer.parseInt(monthStopDate);
        int dayStopD = Integer.parseInt(dayStopDate);

        for (int i=0; i<allExpense.size(); i++){
            Expense expense = allExpense.get(i);     // sparar första Expense från Database
            String date = expense.getDate();         // "date" - sparar datum
            int firstSlash = date.indexOf("/");
            int lastSlash = date.length()- 5;
            year = date.substring(lastSlash+1, date.length());
            month = date.substring(0, firstSlash);
            day = date.substring(firstSlash+1, lastSlash);
            int yearList = Integer.parseInt(year);
            int monthList = Integer.parseInt(month);
            int dayList = Integer.parseInt(day);

            if(yearList >= yearStartD && yearList <=yearStopD &&
                    monthList >= monthStartD & monthList <= monthStopD &&
                    dayList >= dayStartD && dayList <= dayStopD){
                filterList.add(expense);
            }
        }
        return filterList;
    }
}
