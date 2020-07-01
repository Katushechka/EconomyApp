package com.example.p1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FabFragment extends Fragment {
    private GeneralInfo generalInfo;
    FloatingActionButton fab;
    FloatingActionButton fabIncome;
    FloatingActionButton fabExpense;
    Animation FabOpen, FabClose, FabClockwise, FabAnticlockwise;
    boolean isOpen = false;

    public FabFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fab, container, false);;

       fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    fab.startAnimation(FabAnticlockwise);
                    fabIncome.startAnimation(FabClose);
                    fabExpense.startAnimation(FabClose);
                    fabIncome.setClickable(false);
                    fabExpense.setClickable(false);
                    isOpen = false;
                } else {
                    fab.startAnimation(FabClockwise);
                    fabIncome.startAnimation(FabOpen);
                    fabExpense.startAnimation(FabOpen);
                    fabIncome.setClickable(true);
                    fabExpense.setClickable(true);
                    isOpen = true;
                }

            }
        });

        fabIncome = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonIncome);
        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalInfo.btbfabIncomeClicked();
            }
        });


        fabExpense = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonExpense);
        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalInfo.btbfabExpenseClicked();
            }
        });

        FabOpen = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        FabClockwise = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_clockwise);
        FabAnticlockwise = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anticlockwise);

        return view;
    }

    public void setGeneralInfo(GeneralInfo generalInfo){
        this.generalInfo=generalInfo;
    }




}
