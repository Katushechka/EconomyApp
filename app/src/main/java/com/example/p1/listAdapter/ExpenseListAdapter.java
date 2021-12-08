package com.example.p1.listAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p1.R;
import com.example.p1.database.Expense;

import java.util.ArrayList;
import java.util.List;



public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ViewHolder> {
    private OnItemClickListener listener;
    private List<Expense> expenseList = new ArrayList<>();


    @Override
    public ExpenseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_expenses, parent, false);
        return new ExpenseListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ViewHolder holder, int listPosition) {
        Expense currentExpense = expenseList.get(listPosition);
        String livsmedel = "livsmedel";
        String fritid = "fritid";
        String boende = "boende";
        String resor ="resor";
        String övrigt = "övrigt";

        if(currentExpense.getCategory().equals(livsmedel)){
            holder.imageViewCategory.setImageResource(R.drawable.livsmedel);
        }
        if(currentExpense.getCategory().equals(fritid)){
            holder.imageViewCategory.setImageResource(R.drawable.fritid);
        }
        if(currentExpense.getCategory().equals(boende)){
            holder.imageViewCategory.setImageResource(R.drawable.boende);
        }
        if(currentExpense.getCategory().equals(resor)){
            holder.imageViewCategory.setImageResource(R.drawable.resor);
        }
        if(currentExpense.getCategory().equals(övrigt)){
            holder.imageViewCategory.setImageResource(R.drawable.other);
        }

        holder.textViewTitle.setText(currentExpense.getTitle());
        holder.textViewSum.setText(String.valueOf(currentExpense.getSum()));
        holder.textViewData.setText(currentExpense.getDate());
        holder.textViewBarcode.setText(currentExpense.getBarcode());

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenseList = expenses;
        notifyDataSetChanged();
    }

    public Expense getNoteAt(int position) {
        return expenseList.get(position);
    }


    //create ViewHolder class  (holder Views in our RecyclerView items)
     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private ImageView imageViewCategory;
        private TextView textViewSum;
        private TextView textViewData;
        private TextView textViewBarcode;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            imageViewCategory = itemView.findViewById(R.id.image_view_category);
            textViewSum = itemView.findViewById(R.id.text_view_sum);
            textViewData = itemView.findViewById(R.id.text_view_data);
            textViewBarcode = itemView.findViewById(R.id.text_view_barcode);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(expenseList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    public void setOnItemClickListener(ExpenseListAdapter.OnItemClickListener listener){
        this.listener=listener;
    }
}
