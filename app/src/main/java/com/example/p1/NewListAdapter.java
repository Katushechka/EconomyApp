package com.example.p1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewListAdapter extends RecyclerView.Adapter<NewListAdapter.ViewHolder> {
    private List<Income> incomeList = new ArrayList<>();
    private List<Income> expenseList = Collections.emptyList();
    private int sum=0;

    @Override
    public NewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_text_view, parent, false);
        return new NewListAdapter.ViewHolder(itemView);
    }


    //Use the provided ViewHolder on the onCreateViewHolder method to populate the current row on the RecyclerVie
    @Override
    public void onBindViewHolder(@NonNull NewListAdapter.ViewHolder holder, int listPosition) {
        Income currentIncome = incomeList.get(listPosition);
        holder.text_view.setText(String.valueOf(currentIncome.getSum()));

    }


    // //returns the number of elements the RecyclerView will display
    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public void setSum(int sum){
        this.sum=sum;
        notifyDataSetChanged();
    }


    //store the references to the relevant views for one entry in the RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text_view;


        public ViewHolder(View itemView) {
            super(itemView);
            text_view = itemView.findViewById(R.id.text_view);
        }
    }
}
