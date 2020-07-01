package com.example.p1;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class SetScan extends AppCompatActivity {
    private TitelSumDataFragment titelSumDataFragment;
    private ExpenseSpinnerFragment expenseSpinnerFragment;
    private EconomyViewModel economyViewModel;
    private DateFragment dateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_note);

        titelSumDataFragment = new TitelSumDataFragment();
        titelSumDataFragment.setSetScan(this);
        setTitelSumDataFragmen(titelSumDataFragment, false);


        expenseSpinnerFragment = new ExpenseSpinnerFragment();
        setiIcomeExpenseSpinnerFragment(expenseSpinnerFragment, false);
   //     expenseSpinnerFragment.setSetScan(this);



        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    public void setiIcomeExpenseSpinnerFragment (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.upper_container,(Fragment)fragment);
        fragmentTransaction.commit();
    }

    public void setTitelSumDataFragmen (Fragment fragment, boolean backstack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lower_container,(Fragment)fragment);
        fragmentTransaction.commit();
    }

    public void showDatePicker(){
        dateFragment = new DateFragment();
        dateFragment.show(getSupportFragmentManager(), "datepicker");
    }


    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

       /* dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this, "Date: " + dateMessage,Toast.LENGTH_SHORT).show();*/

    }


}
