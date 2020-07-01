package com.example.p1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class IncomeSpinnerFragment extends Fragment {
    private Spinner spinner;
    private SetNote setNote;
    private String category = "";

    public IncomeSpinnerFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_spinner, container, false);
        spinner = (Spinner)view.findViewById(R.id.spinnerChooseCategory);

        setUpSpinner();

        return view;



    }

    public void setSetNote(SetNote setNote){
        this.setNote=setNote;
    }

    public void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.incomeCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new CategorySpinner());
    }



    private class CategorySpinner implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
            category = (String) parent.getSelectedItem();
            spinner.setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.colorWhite));
        /*    if (parent.getSelectedItemPosition() > 0) {
                //category = (String) parent.getSelectedItem();
                spinner.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.colorWhite));

            } else {
                category = "";
            }*/
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public String getSpinner(){
        return spinner.getSelectedItem().toString();
    }
}
