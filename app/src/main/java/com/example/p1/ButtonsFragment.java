package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ButtonsFragment extends Fragment {
    int status = 0;
    private GeneralInfo generalInfo;
    private Button btnIncomes;
    private Button btnIExpenses;
    private Button btnGeneralInfo;
    private Button btnScann;


    public ButtonsFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buttons, container, false);
        btnIncomes = (Button)view.findViewById(R.id.btnIncomes);
        btnIncomes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 1;
                Intent intent = new Intent (getContext(), ShowIncomes.class);
                startActivity(intent);
     //           generalInfo.btnSetIncomesClicked();
            }
        });
        btnIExpenses = (Button)view.findViewById(R.id.btnExpenses);
        btnIExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 2;
                Intent intent = new Intent (getContext(), ShowExpense.class);
                startActivity(intent);
            //    generalInfo.btnSetExpenseClicked();
            }
        });
        btnGeneralInfo = (Button)view.findViewById(R.id. btnGeneralInfo);
        btnGeneralInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalInfo.btnGeneralInfoClicked();
            }
        });
        btnScann = (Button)view.findViewById(R.id. btnScann);
        btnScann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalInfo.scanCode(view);
            }
        });

        return view;


    }

    public void setGeneralInfo(GeneralInfo generalInfo){
        this.generalInfo=generalInfo;
    }
}
