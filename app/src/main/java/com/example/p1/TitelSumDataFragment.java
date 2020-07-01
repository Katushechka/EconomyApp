package com.example.p1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class TitelSumDataFragment extends Fragment {
    private SetNote setNote;
    private SetScan setScan;
    private EditText edit_text_title;
    private EditText edit_text_sum;
    private Button data;


    public TitelSumDataFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titel_sum_data, container, false);
        edit_text_title = (EditText) view.findViewById(R.id.edit_text_title);
        edit_text_sum = (EditText) view.findViewById(R.id.edit_text_sum);
        data = (Button) view.findViewById(R.id.data);

        data.setTextColor(ContextCompat.getColor(getContext(), R.color.colorInputField));
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNote.showDatePicker();
            }
        });
        return view;


    }

    public void setSetNote(SetNote setNote){
        this.setNote=setNote;
    }
    public void setSetScan(SetScan setScan){
        this.setScan=setScan;

    }

    public String getTitle(){
        return  edit_text_title.getText().toString();
    }
    public String getSum(){
        return  edit_text_sum.getText().toString();
    }
}
