package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.p1.database.Expense;
import com.example.p1.database.Income;
import com.example.p1.fragments.DateFragment;
import com.example.p1.fragments.ExpenseSpinnerFragment;
import com.example.p1.fragments.IncomeSpinnerFragment;
import com.example.p1.fragments.TitelSumDataFragment;

public class SetNote extends AppCompatActivity {
    private EconomyViewModel economyViewModel;
    private TitelSumDataFragment titelSumDataFragment;
    private IncomeSpinnerFragment incomeSpinnerFragment;
    private ExpenseSpinnerFragment expenseSpinnerFragment;
    static final String CHOICE = "choice" ;
    private String incomeOrExpense;
    private EditText editTextTitle;
    private EditText editTextSum;
    private Spinner spinner;
    private String category;
    private Button date;
    private String dateMessage;
    private Button add;
    private Button viewAll;
    private DateFragment dateFragment;
    static final String BARCODE = "barcode";
    private String barcode;
    private String title;
    private String sum;
    private String kategorySpinner;

    public static final String EXTRA_ID =
            "com.example.p1.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.p1.EXTRA_TITLE";

    public static final String EXTRA_SUM =
            "com.example.p1.EXTRA_SUM";

    public static final String EXTRA_CATEGORY =
            "com.example.p1.EXTRA_CATEGORY";

    public static final String EXTRA_DATE =
            "com.example.p1.EXTRA_DATE";

    public static final String EXTRA_BARCODE =
            "com.example.p1.EXTRA_BARCODE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_note);

        titelSumDataFragment = new TitelSumDataFragment();
        titelSumDataFragment.setSetNote(this);
        setTitelSumDataFragmen(titelSumDataFragment, false);

        incomeSpinnerFragment = new IncomeSpinnerFragment();
        incomeSpinnerFragment.setSetNote(this);

        expenseSpinnerFragment = new ExpenseSpinnerFragment();
        expenseSpinnerFragment.setSetNote(this);

        choiceCategoryFragment();
        getBarcode();

        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }


    public boolean choiceCategoryFragment() {
        Intent intent = getIntent();
        String choice = intent.getStringExtra(CHOICE);
        int result= Integer.parseInt(choice);
        if (result ==1 ){
            setiIcomeExpenseSpinnerFragment(incomeSpinnerFragment, false);
            incomeOrExpense = "income";
            return true;
        } else {
            setiIcomeExpenseSpinnerFragment(expenseSpinnerFragment, false);
            incomeOrExpense = "expense";
        }
        return false;
    }

    public String getBarcode(){
        Intent intent = getIntent();
        barcode = intent.getStringExtra(BARCODE);
        return barcode;
    }


    private void saveNote() {
        String title =  titelSumDataFragment.getTitle();
        String sum = titelSumDataFragment.getSum().trim();
        String kategorySpinner = incomeSpinnerFragment.getSpinner();
        category=kategorySpinner;
        if (title.trim().isEmpty() || sum.trim().isEmpty()|| kategorySpinner.trim().isEmpty() ) {
            Toast.makeText(this, "Please insert a title, summa, date and category", Toast.LENGTH_SHORT).show();
            return;
        }
        int summa = Integer.parseInt(sum.trim());
        Income income = new Income(title, summa, category, dateMessage);
        economyViewModel.insert(income);

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void saveExpense() {
        title =  titelSumDataFragment.getTitle();
        sum = titelSumDataFragment.getSum().trim();
        kategorySpinner = expenseSpinnerFragment.getSpinner();
        category=kategorySpinner;

        if (title.trim().isEmpty() || sum.trim().isEmpty()|| kategorySpinner.trim().isEmpty() ) {
            Toast.makeText(this, "Please insert a title, summa, date and category", Toast.LENGTH_SHORT).show();
            return;
        }
        int summa = Integer.parseInt(sum.trim());
        if(barcode!=null) {
            Expense expense = new Expense(title, summa, category, dateMessage, barcode);
            economyViewModel.insertExpense(expense);
        }else{
            Expense expense = new Expense(title, summa, category, dateMessage, null);
            economyViewModel.insertExpense(expense);
        }
        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
    }


   //Get Save-button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }


    // Button save is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                if (incomeOrExpense == "income") {
                    String title =  titelSumDataFragment.getTitle();
                    String sum = titelSumDataFragment.getSum().trim();
                    String kategorySpinner = incomeSpinnerFragment.getSpinner();
                    if (title.trim().isEmpty() || sum.trim().isEmpty()|| kategorySpinner.trim().isEmpty() ) {
                        Toast.makeText(this, "Please insert a title, summa, date and category", Toast.LENGTH_SHORT).show();
                    } else {
                        saveNote();
                        Intent intent = new Intent(SetNote.this, ShowIncomes.class);
                        startActivity(intent);
                    }
                }
                if (incomeOrExpense == "expense") {
                    String title =  titelSumDataFragment.getTitle();
                    String sum = titelSumDataFragment.getSum().trim();
                    String kategorySpinner = expenseSpinnerFragment.getSpinner();
                    if (title.trim().isEmpty() || sum.trim().isEmpty()|| kategorySpinner.trim().isEmpty() ) {
                        Toast.makeText(this, "Please insert a title, summa, date and category", Toast.LENGTH_SHORT).show();
                    } else {

                        saveExpense();
                        Intent intent = new Intent(SetNote.this, ShowExpense.class);
                        startActivity(intent);
                    }
                }
                /*Intent intent = new Intent (SetNote.this, ShowIncomes.class);
                startActivity(intent);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDatePicker(){
        dateFragment = new DateFragment();
        dateFragment.show(getSupportFragmentManager(), "datepicker");
    }


    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this, "Date: " + dateMessage,Toast.LENGTH_SHORT).show();

    }
    //--------------------------------------------------------------------------------


    public void setFragment(Fragment fragment, boolean backstack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, fragment);
        if(backstack) {
            ft.addToBackStack(null);
        }
        ft.commit();
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
}
