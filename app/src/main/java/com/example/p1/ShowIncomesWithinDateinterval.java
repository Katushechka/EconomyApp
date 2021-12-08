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


import com.example.p1.database.Income;
import com.example.p1.listAdapter.EconomyListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowIncomesWithinDateinterval extends AppCompatActivity {
    private Dateinterval dateinterval;
    private EconomyViewModel economyViewModel;
    private static final String STARTDATE = "startDate";
    private static final String STOPDATE = "stopDate";
    private String startdate;
    private String stopdate;
    private LiveData<List<Income>> allNotes;   //ta inte bort
    private int totalIncome;
    static final String TOTALINCOME = "totalIncome";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_within_dateinterval);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Initialize the RecyclerView with a layout manager, create an instanse of the adapter
        // and assign that instance to the RecycklerView object
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        final EconomyListAdapter adapter = new EconomyListAdapter();
        recyclerView.setAdapter(adapter);

        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);
        LiveData<List<Income>> allIncome = economyViewModel.getAllNotes();
        allIncome.observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(@Nullable List<Income> incomes) {
                adapter.setIncomes(filterIncomeForDate(incomes));
            }
        });
        getDate();
    }

    public void getDate(){
        Intent intent = getIntent();
        startdate =intent.getStringExtra(STARTDATE);
        stopdate = intent.getStringExtra(STOPDATE);
    }

    private List<Income> filterIncomeForDate (List<Income> allIncome){
        List<Income> filterList = new ArrayList<Income>();
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

        for (int i=0; i<allIncome.size(); i++){
            Income income = allIncome.get(i);     // sparar första Income från Database
            String date = income.getDate();         // "date" - sparar datum
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
                filterList.add(income);
            }
        }
        return filterList;
    }
}
