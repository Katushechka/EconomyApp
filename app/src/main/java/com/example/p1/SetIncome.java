package com.example.p1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SetIncome extends AppCompatActivity {
    private Spinner spinner;
    private String category;

    private DateFragment dateFragment;
    private Button date;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setincome);  //category






        date = findViewById(R.id.data);
        date.setTextColor(ContextCompat.getColor(this, R.color.colorInputField));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    //---------------------------------------------------------------------------
    //Spinner Category
    //------------------------------------------------------------------------

    public void setUpSpinner() {
        spinner = findViewById(R.id.spinnerChooseCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.incomeCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new CategorySpinner());
    }

    private class CategorySpinner implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
            if (parent.getSelectedItemPosition() > 0) {
                category = (String) parent.getSelectedItem();
                spinner.setBackgroundColor(ContextCompat.getColor(SetIncome.this,
                        R.color.colorWhite));
            } else {
                category = "";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
     //-----------------------------------------------------------------------


    public void setFragment (Fragment fragment, boolean backstack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, fragment);
        if(backstack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }



//---------------------------------------------------------------------------------------
//Date
//------------------------------------------------------------------------------


    public void showDatePicker(){
        dateFragment = new DateFragment();
        dateFragment.show(getSupportFragmentManager(), "datepicker");
    }


    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this, "Date: " + dateMessage,Toast.LENGTH_SHORT).show();

    }
}
//--------------------------------------------------------------------------------------
