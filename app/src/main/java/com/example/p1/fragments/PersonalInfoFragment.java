package com.example.p1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.p1.R;

public class PersonalInfoFragment extends Fragment {
    private TextView textViewName;
    private TextView textViewSurname;
    private String name;
    private String surname;

    public PersonalInfoFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalinfo, container, false);
        textViewName = (TextView)view.findViewById(R.id.textViewName);
        textViewSurname = (TextView)view.findViewById(R.id.textViewSurname);
        return view;


    }

    public void setText(String name, String surname){
        this.name=name;
        this.surname=surname;
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewName.setText(name);
        textViewSurname.setText(surname);
    }
}
