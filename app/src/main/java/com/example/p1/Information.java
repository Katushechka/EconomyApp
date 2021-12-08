package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.p1.database.Expense;
import com.example.p1.database.Income;

import java.util.ArrayList;
import java.util.List;

public class Information extends AppCompatActivity {

    private Dateinterval dateinterval;
    private EconomyViewModel economyViewModel;
    private static final String STARTDATE = "startDate";
    private static final String STOPDATE = "stopDate";
    private String startdate;
    private String stopdate;
    private LiveData<List<Income>> allNotes;
    private int totalIncome;
    private int totalExpense;
    private int totalSalary;
    static final String TOTALINCOME = "totalIncome";
    private TextView textViewYourName;
    private TextView textViewName;
    private TextView textViewTotalIncome;
    private TextView textViewTotalExpense;
    private TextView textViewResult;
    private TextView text_view_total_income;
    static final String NAME = "name";
    static final String SURNAME = "surname";
    private String name;
    private String surname;
    private Income income;
    private LiveData<List<Income>> allIncome;
    private int total;
    private ShowIncomesWithinDateinterval showIncomesWithinDateinterval;
    private AnyChartView anyChartView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        textViewYourName = findViewById(R.id.textViewYourName);
        textViewName = findViewById(R.id.textViewName);
        textViewTotalIncome = findViewById(R.id.textViewTotalIncome);
        textViewTotalExpense = findViewById(R.id.textViewTotalExpense);
        textViewResult = findViewById(R.id.textViewResult);;
        setPersonalInfo();

        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);
        LiveData<List<Income>> allIncome = economyViewModel.getAllNotes();
        allIncome.observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(@Nullable List<Income> incomes) {
                getTotalIncome(incomes);
                setTotalIncome();
                getTotalSalary(incomes);
                redundanceDeficit();
                setupPieChart();
            }
        });

        LiveData<List<Expense>> allExpense = economyViewModel.getAllExpenses();
        allExpense.observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expense) {
                //             adapter.setIncomes(filterIncomeForDate(incomes));
                getTotalExpense(expense);
                setTotalExpense();
                redundanceDeficit();
                setupPieChart();
            }
        });

        anyChartView = findViewById(R.id.any_chart_view);
    }

    public void setPersonalInfo(){
        Intent intent = getIntent();
        name = intent.getStringExtra(NAME);
        surname = intent.getStringExtra(SURNAME);
        textViewName.setText(" " + name +" " + surname);
    }

    public void setTotalIncome(){
        String setTotIncome = String.valueOf(totalIncome);
        textViewTotalIncome.setText(" " + setTotIncome);
    }

    private int getTotalIncome(List<Income> allIncome){
        for (int i=0; i<allIncome.size(); i++) {
            Income income = allIncome.get(i);     // sparar första Income
            int sum = income.getSum();              //sum
            totalIncome += sum;
        }
        return totalIncome;
    }

    private int getTotalExpense(List<Expense> allExpense){
        for (int i=0; i<allExpense.size(); i++) {
            Expense expense = allExpense.get(i);     // sparar första Expense
            int sum = expense.getSum();              //sum
            totalExpense += sum;
        }
        return totalExpense;
    }

    public void setTotalExpense(){
        String setTotExpense = String.valueOf(totalExpense);
        textViewTotalExpense.setText(" " + setTotExpense);
    }

    public void redundanceDeficit(){
        int resultat = totalIncome - totalExpense;
        if (resultat > 0){
            textViewResult.setText("Överskott är " + resultat);
        }
        else {
            textViewResult.setText(("Underskott är " + resultat));
        }
    }

    public void setupPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        String[] incomeExpense = {"inkomst", "utgift"};
        int[] sum = {totalIncome, totalExpense};

        for (int i =0; i<incomeExpense.length; i++){
            dataEntries.add(new ValueDataEntry(incomeExpense[i], sum[i]));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }

    private int getTotalSalary(List<Income> allIncome){
        int sumSalary =0;

        for (int i=0; i<allIncome.size(); i++) {
            Income income = allIncome.get(i);     // sparar första Income

            if (income.getCategory() == "lön"){
                sumSalary = income.getSum();
            }
            totalSalary += sumSalary;
        }
        return totalSalary;
    }
}
